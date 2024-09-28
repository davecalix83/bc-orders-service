package com.cuscatlan.orders.domain.exception;

/**
 * Exception thrown when no order records are found.
 */
public class NoOrdersFoundException extends RuntimeException {

    /**
     * Constructs a NoOrdersFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public NoOrdersFoundException(String message) {
        super(message);
    }
}
