package com.cuscatlan.orders.application.service;

import com.cuscatlan.orders.application.dto.ProductDto;
import com.cuscatlan.orders.domain.exception.ProductNotFoundException;
import com.cuscatlan.orders.infrastructure.external.ProductServiceClient;
import org.springframework.stereotype.Component;

/**
 * Service for validating product information.
 * This service interacts with an external product service to ensure products exist before processing orders.
 */
@Component
public class ProductValidationService {

    private final ProductServiceClient productServiceClient;

    /**
     * Constructs a ProductValidationService with the given ProductServiceClient.
     *
     * @param productServiceClient the client to interact with the external product service
     */
    public ProductValidationService(ProductServiceClient productServiceClient) {
        this.productServiceClient = productServiceClient;
    }

    /**
     * Validates the existence of a product by its ID.
     *
     * @param productId the ID of the product to validate
     * @return ProductDto containing the product information
     * @throws ProductNotFoundException if the product with the given ID does not exist
     */
    public ProductDto validateProduct(Long productId) {
        ProductDto product = productServiceClient.getProductById(productId);
        if (product == null) {
            throw new ProductNotFoundException(productId);
        }
        return product;
    }
}
