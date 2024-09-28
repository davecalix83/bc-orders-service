package com.cuscatlan.orders.domain.exception;

/**
 * Exception thrown when a product is not found.
 */
public class ProductNotFoundException extends RuntimeException {

    /**
     * Constructs a ProductNotFoundException with a specified product ID.
     *
     * @param productId the ID of the product that was not found
     */
    public ProductNotFoundException(Long productId) {
        super("Product not found: " + productId);
    }
}
