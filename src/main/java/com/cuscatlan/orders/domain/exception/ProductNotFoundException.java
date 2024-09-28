package com.cuscatlan.orders.domain.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long productId) {
        super("Product not found: " + productId);
    }
}
