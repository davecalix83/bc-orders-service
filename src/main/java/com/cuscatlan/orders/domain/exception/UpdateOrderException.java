package com.cuscatlan.orders.domain.exception;

/**
 * Exception thrown when an order update operation fails.
 */
public class UpdateOrderException extends RuntimeException {

    /**
     * Constructs an UpdateOrderException with a specified message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public UpdateOrderException(String message) {
        super(message);
    }
}
