package com.ing.brokercore.services;

import com.ing.brokercore.entities.Asset;
import com.ing.brokercore.entities.Customer;
import com.ing.brokercore.entities.Orders;
import com.ing.brokercore.enums.OrderStatus;
import com.ing.brokercore.enums.OrderType;
import com.ing.brokercore.repositories.AssetRepository;
import com.ing.brokercore.repositories.CustomerRepository;
import com.ing.brokercore.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Transactional
    public Orders createOrder(Long customerId, String assetName, String type, Double size, Double price) throws Exception {

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new Exception("Customer not found"));

        Asset asset = assetRepository.findByCustomerAndAssetName(customer, assetName).orElseThrow(() -> new Exception("Asset not found"));


        if (type.equals(OrderType.SELL.toString()) && asset.getUsableSize() < size) {
            throw new Exception("Not enough asset size to sell");
        }

        Orders order = new Orders();
        order.setCustomer(customer);
        order.setAssetName(assetName);
        order.setOrderType(OrderType.valueOf(type));
        order.setSize(size);
        order.setPrice(price);
        order.setStatus(OrderStatus.PENDING);
        order.setCreateDate(new Date());

        return orderRepository.save(order);

    }

    public List<Orders> getOrderListForCustomer(Long customerId, Date startDate, Date endDate) throws Exception {

        List<Orders> orderList = orderRepository.findByCustomerIdAndCreateDateBetween(customerId, startDate, endDate);

        if (orderList.isEmpty()) {
            throw new Exception("Customer " + customerId + " doesn't have any orders.");
        }

        return orderList;
    }

    public Orders deleteOrderById(Long orderId) throws Exception {
        Orders order = orderRepository.findById(orderId).orElseThrow(()-> new Exception("Order not found"));

        if ( !order.getStatus().equals(OrderStatus.PENDING)) {
            throw new Exception("Order status is " + order.getStatus() + ", You can only delete orders by status pending.");
        }

        orderRepository.delete(order);
        return order;
    }
}
