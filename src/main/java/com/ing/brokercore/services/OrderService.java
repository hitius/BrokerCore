package com.ing.brokercore.services;

import com.ing.brokercore.entities.Asset;
import com.ing.brokercore.entities.Customer;
import com.ing.brokercore.entities.Orders;
import com.ing.brokercore.enums.OrderSide;
import com.ing.brokercore.enums.OrderStatus;
import com.ing.brokercore.repositories.AssetRepository;
import com.ing.brokercore.repositories.CustomerRepository;
import com.ing.brokercore.repositories.OrderRepository;
import com.ing.brokercore.utils.BusinessException;
import com.ing.brokercore.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.ing.brokercore.utils.BusinessException.*;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AssetService assetService;

    /**
     * Creates order. checks size for able to selling
     * @param customerId
     * @param assetName
     * @param orderSide
     * @param size
     * @param price
     * @return
     * @throws Exception
     */
    @Transactional
    public Orders createOrder(Long customerId, String assetName, OrderSide orderSide, Double size, Double price) throws Exception {

        Customer customer = customerService.getCustomerById(customerId);

        Asset asset = assetService.findByCustomerIdAndAssetName(customer.getId(), assetName);

        if (orderSide.equals(OrderSide.SELL) && asset.getUsableSize() < size) {
            throw new BusinessException(NOT_ENOUGH_ASSET_TO_SELL);
        }

        if ( !assetName.equals(Constants.TRY)) {
            throw new BusinessException(INVALID_CURRENCY);
        }

        Orders order = new Orders();
        order.setCustomer(customer);
        order.setAssetName(assetName);
        order.setOrderSide(orderSide);
        order.setSize(size);
        order.setPrice(price);
        order.setStatus(OrderStatus.PENDING);
        order.setCreateDate(new Date());

        asset.setUsableSize(asset.getUsableSize() - size);
        assetRepository.save(asset);

        return orderRepository.save(order);

    }

    /**
     * gives order list. not showing cancelled
     * @param customerId
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public List<Orders> getOrderListForCustomer(Long customerId, Date startDate, Date endDate) throws Exception {

        List<Orders> orderList = orderRepository.findByCustomerIdAndCreateDateBetweenAndStatusIsNot(customerId, startDate, endDate, OrderStatus.CANCELLED);

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

        Asset asset = assetService.findByCustomerIdAndAssetName(order.getCustomer().getId(), Constants.TRY);

        // changing status to cancelled
        order.setStatus(OrderStatus.CANCELLED);

        // adding cancelled value to its asset
        asset.setUsableSize(asset.getUsableSize() + order.getSize());

        orderRepository.save(order);
        assetRepository.save(asset);
        return order;
    }

    /**
     * This method matchs pending orders
     * @param orderId
     * @return
     * @throws BusinessException
     */
    @Transactional
    public Orders matchPendingOrder(Long orderId) throws BusinessException {
        Orders order = findOrderByOrderId(orderId);

        if (!order.getStatus().equals(OrderStatus.PENDING)) {
            throw new BusinessException(BusinessException.ORDER_IS_NOT_PENDING);
        }

        Asset tryAsset = assetService.findByCustomerIdAndAssetName(order.getCustomer().getId(), Constants.TRY);

        if (tryAsset.getUsableSize() < order.getSize()) {
            throw new BusinessException(INSUFFICIENT_FUNDS);
        }

        // update size try asset
        tryAsset.setUsableSize(tryAsset.getUsableSize() - order.getSize());

        Asset asset = assetService.findByCustomerIdAndAssetName(order.getCustomer().getId(), order.getAssetName());

        // update ammont
        asset.setSize(asset.getSize() + order.getSize());

        // set as mathed
        order.setStatus(OrderStatus.MATCHED);

        assetRepository.save(tryAsset);
        assetRepository.save(asset);
        return orderRepository.save(order);
    }

    /**
     *  finds orders by id
     * @param orderId
     * @return
     * @throws BusinessException
     */
    private Orders findOrderByOrderId(Long orderId) throws BusinessException {
        return orderRepository.findById(orderId).orElseThrow(() -> new BusinessException(ORDER_NOT_FOUND));
    }
}
