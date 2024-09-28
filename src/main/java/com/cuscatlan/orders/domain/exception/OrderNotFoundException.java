package com.cuscatlan.orders.domain.exception;

/**
 * Exception thrown when an order is not found.
 */
public class OrderNotFoundException extends RuntimeException {

    /**
     * Constructs an OrderNotFoundException with a specified order ID.
     *
     * @param orderId the ID of the order that was not found
     */
    public OrderNotFoundException(Long orderId) {
        super("Order not found: " + orderId);
    }
}
