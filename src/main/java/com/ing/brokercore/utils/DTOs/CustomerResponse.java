package com.ing.brokercore.utils.DTOs;

import com.ing.brokercore.entities.Customer;

public class CustomerResponse {
    private String message;
    private Customer customer;

    // Constructor
    public CustomerResponse(String message, Customer customer) {
        this.message = message;
        this.customer = customer;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
