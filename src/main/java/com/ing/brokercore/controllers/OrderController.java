package com.ing.brokercore.controllers;

import com.ing.brokercore.entities.Orders;
import com.ing.brokercore.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Orders> createOrder(@RequestParam Long customerId,
                              @RequestParam String assetName,
                              @RequestParam String type,
                              @RequestParam Double size,
                              @RequestParam Double price) throws Exception {

        Orders order = orderService.createOrder(customerId, assetName, type, size, price);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/listOrders")
    public ResponseEntity<List<Orders>> listOrders(@RequestParam Long customerId,
                                                   @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
                                                   @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate) throws Exception {

        List<Orders> ordersList = orderService.getOrderListForCustomer( customerId, startDate, endDate);
        return ResponseEntity.ok(ordersList);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Orders> deleteOrder(@RequestParam Long orderId) throws Exception {

        Orders order = orderService.deleteOrderById(orderId);
        return ResponseEntity.ok(order);
    }

}
