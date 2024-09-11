package com.ing.brokercore.controllers;

import com.ing.brokercore.entities.Customer;
import com.ing.brokercore.services.CustomerService;
import com.ing.brokercore.utils.BusinessException;
import com.ing.brokercore.utils.DTOs.CustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<CustomerResponse> register(@RequestBody Customer customer) {
        customer = customerService.savePassword(customer);

        CustomerResponse response = new CustomerResponse("User registered successfully", customer);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<Customer> getProfile(@RequestParam String username) throws BusinessException {
        Customer customer = customerService.getProfile(username);
        return ResponseEntity.ok(customer);
    }

}
