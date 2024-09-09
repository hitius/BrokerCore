package com.ing.brokercore.controllers;

import com.ing.brokercore.entities.Customer;
import com.ing.brokercore.repositories.CustomerRepository;
import com.ing.brokercore.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/depositMoney")
    public ResponseEntity<Customer> depositMoney(@RequestParam Long customerId,
                                       @RequestParam Double amount) throws Exception {

        Customer customer = customerService.depositMoney(customerId, amount);
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/withdrawMoney")
    public ResponseEntity<Customer> withdrawMoney(@RequestParam Long customerId,
                                                  @RequestParam Double amount,
                                                  @RequestParam String iban) throws Exception {

        Customer customer = customerService.withdrawMoney(customerId, amount, iban);
        return ResponseEntity.ok(customer);
    }


}
