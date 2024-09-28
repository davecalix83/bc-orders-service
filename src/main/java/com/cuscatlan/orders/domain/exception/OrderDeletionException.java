package com.cuscatlan.orders.domain.exception;

/**
 * Exception thrown when there is an error during order deletion.
 */
public class OrderDeletionException extends RuntimeException {

    /**
     * Constructs an OrderDeletionException with the specified detail message.
     *
     * @param message the detail message
     */
    public OrderDeletionException(String message) {
        super(message);
    }
}
