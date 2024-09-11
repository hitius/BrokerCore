package com.ing.brokercore.controllers;

import com.ing.brokercore.entities.Orders;
import com.ing.brokercore.enums.OrderSide;
import com.ing.brokercore.services.OrderService;
import com.ing.brokercore.utils.DTOs.OrderRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@Validated
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * create order for customer by given params
     * @param customerId
     * @param assetName
     * @param type
     * @param size
     * @param price
     * @return
     * @throws Exception
     */
    @PostMapping("/create")
    public ResponseEntity<Orders> createOrder(@Valid @RequestBody OrderRequest orderRequest) throws Exception {

        Orders order = orderService.createOrder(orderRequest.getCustomerId(), orderRequest.getAssetName(), OrderSide.valueOf(orderRequest.getOrderSide()), orderRequest.getSize(), orderRequest.getPrice());
        return ResponseEntity.ok(order);
    }

    /**
     * lists orders for customer in given date range
     * Date format is dd-MM-yyyy
     * @param customerId
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    @GetMapping("/listOrders")
    public ResponseEntity<List<Orders>> listOrders(@RequestParam Long customerId,
                                                   @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
                                                   @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate) throws Exception {

        List<Orders> ordersList = orderService.getOrderListForCustomer( customerId, startDate, endDate);
        return ResponseEntity.ok(ordersList);
    }

    /**
     * delete order if only status is PENDING
     * @param orderId
     * @return
     * @throws Exception
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Orders> deleteOrder(@RequestParam Long orderId) throws Exception {

        Orders order = orderService.deleteOrderById(orderId);
        return ResponseEntity.ok(order);
    }

}
