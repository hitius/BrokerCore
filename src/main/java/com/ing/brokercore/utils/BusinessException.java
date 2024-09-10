package com.ing.brokercore.utils;

public class BusinessException extends Exception {

    public static final String INVALID_CURRENCY = "Invalid currency, only TRY is allowed.";
    public static final String NEGATIVE_AMOUNT = "Amount must be greater than zero.";
    public static final String PROBLEM_ON_MONEY_TRANSFER = "Problem on money transfer";
    public static final String INVALID_IBAN = "Invalid IBAN format.";
    public static final String CUSTOMER_NOT_FOUND = "Customer not found.";
    public static final String INSUFFICIENT_FUNDS = "Insufficient funds for withdrawal.";
    public static final String ASSET_NOT_FOUND = "Asset not found.";
    public static final String CUSTOMER_DOES_NOT_HAVE_ANY_ASSET = "Customer does not have any asset.";
    public static final String NOT_ENOUGH_ASSET_TO_SELL = "Not enough asset size to sell";
    public static final String CUSTOMER_DOES_NOT_HAVE_ANY_ORDER = "Customer does not have any order.";
    public static final String ORDER_NOT_FOUND = "Order not found.";
    public static final String ONLY_PENDING_ORDERS_ALLOWED_TO_DELETE = "You can only delete orders by status PENDING. Your order status is: ";

    public BusinessException(String message) {
        super(message);
    }
}
