package com.ing.brokercore.services;

import com.ing.brokercore.entities.Asset;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MoneyTransferService {

    private final RestTemplate restTemplate;

    public MoneyTransferService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Boolean someMoneyTransferMethod(Asset asset, Double amount, String iban) {
        String url = "http://localhost:8080/money-transfer/transfer?amount=" + amount + "&iban=" + iban;
        return restTemplate.postForObject(url, null, Boolean.class);
    }
}
