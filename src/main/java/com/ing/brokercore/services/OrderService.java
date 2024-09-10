package com.ing.brokercore.services;

import com.ing.brokercore.entities.Asset;
import com.ing.brokercore.entities.Customer;
import com.ing.brokercore.entities.Orders;
import com.ing.brokercore.enums.OrderSide;
import com.ing.brokercore.enums.OrderStatus;
import com.ing.brokercore.enums.OrderSide;
import com.ing.brokercore.repositories.AssetRepository;
import com.ing.brokercore.repositories.CustomerRepository;
import com.ing.brokercore.repositories.OrderRepository;
import com.ing.brokercore.utils.BusinessException;
import com.ing.brokercore.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static com.ing.brokercore.utils.BusinessException.*;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private CustomerService customerService;

    /**
     * Creates order. checks size for able to selling
     * @param customerId
     * @param assetName
     * @param type
     * @param size
     * @param price
     * @return
     * @throws Exception
     */
    @Transactional
    public Orders createOrder(Long customerId, String assetName, String type, Double size, Double price) throws Exception {

        Customer customer = customerService.getCustomerById(customerId);

        Asset asset = assetRepository.findByCustomerAndAssetName(customer, assetName).orElseThrow(() -> new BusinessException(BusinessException.ASSET_NOT_FOUND));

        if (type.equals(OrderSide.SELL.toString()) && asset.getUsableSize() < size) {
            throw new BusinessException(NOT_ENOUGH_ASSET_TO_SELL);
        }

        if ( !assetName.equals(Constants.TRY)) {
            throw new BusinessException(INVALID_CURRENCY);
        }

        Orders order = new Orders();
        order.setCustomer(customer);
        order.setAssetName(assetName);
        order.setOrderSide(OrderSide.valueOf(type));
        order.setSize(size);
        order.setPrice(price);
        order.setStatus(OrderStatus.PENDING);
        order.setCreateDate(new Date());

        asset.setUsableSize(asset.getUsableSize() - size);
        assetRepository.save(asset);

        return orderRepository.save(order);

    }

    /**
     * gives order list
     * @param customerId
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public List<Orders> getOrderListForCustomer(Long customerId, Date startDate, Date endDate) throws Exception {

        List<Orders> orderList = orderRepository.findByCustomerIdAndCreateDateBetween(customerId, startDate, endDate);

        if (orderList.isEmpty()) {
            throw new BusinessException(CUSTOMER_DOES_NOT_HAVE_ANY_ORDER);
        }

        return orderList;
    }

    /**
     * deletes order. actually not deleting but changing status to CANCELLED
     * only status PENDING is allowed
     * @param orderId
     * @return
     * @throws Exception
     */
    public Orders deleteOrderById(Long orderId) throws Exception {
        Orders order = orderRepository.findById(orderId).orElseThrow(()-> new BusinessException(BusinessException.ORDER_NOT_FOUND));

        if ( !order.getStatus().equals(OrderStatus.PENDING)) {
            throw new BusinessException(ONLY_PENDING_ORDERS_ALLOWED_TO_DELETE + order.getStatus());
        }

        Asset asset = assetRepository.findByCustomerIdAndAssetName(order.getCustomer().getId(), Constants.TRY)
                .orElseThrow(() -> new BusinessException(ASSET_NOT_FOUND));

        // changing status to cancelled
        order.setStatus(OrderStatus.CANCELLED);

        // adding cancelled value to its asset
        asset.setUsableSize(asset.getUsableSize() + order.getSize());

        orderRepository.save(order);
        assetRepository.save(asset);
        return order;
    }
}
