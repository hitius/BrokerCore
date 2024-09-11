package com.ing.brokercore.controllers;

import org.springframework.web.bind.annotation.*;

/**
 * this controller is just for microservice
 */
@RestController
@RequestMapping("/money-transfer")
public class MoneyTransferController {

    /**
     * this is for moneytransfer to iban
     * @param amount
     * @param iban
     * @return
     */
    @PostMapping("/transfer")
    public Boolean transfer(@RequestParam Double amount, @RequestParam String iban) {
        if( amount > 0 && iban != null && !iban.isEmpty()) {
            return true;
        }
        return false;
    }
}
