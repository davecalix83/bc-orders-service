package com.cuscatlan.orders.domain.exception;

public class OrderDeletionException extends RuntimeException {

    public OrderDeletionException(String message) {
        super(message);
    }
}
