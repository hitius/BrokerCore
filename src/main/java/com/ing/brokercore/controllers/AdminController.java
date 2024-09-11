package com.ing.brokercore.controllers;

import com.ing.brokercore.entities.Orders;
import com.ing.brokercore.services.OrderService;
import com.ing.brokercore.utils.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/orders")
public class AdminController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/match/{orderId}")
    public ResponseEntity<Orders> matchOrder(@PathVariable Long orderId) throws BusinessException {

        Orders order = orderService.matchPendingOrder(orderId);
        return ResponseEntity.ok(order);

    }

}
