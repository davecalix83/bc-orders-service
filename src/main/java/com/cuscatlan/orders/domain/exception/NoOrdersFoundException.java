package com.cuscatlan.orders.domain.exception;

public class NoOrdersFoundException extends RuntimeException {
    public NoOrdersFoundException(String message) {
        super(message);
    }
}
