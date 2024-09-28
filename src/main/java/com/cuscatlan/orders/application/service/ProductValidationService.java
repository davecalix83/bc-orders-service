package com.cuscatlan.orders.application.service;

import com.cuscatlan.orders.application.dto.ProductDto;
import com.cuscatlan.orders.domain.exception.ProductNotFoundException;
import com.cuscatlan.orders.infrastructure.external.ProductServiceClient;
import org.springframework.stereotype.Component;


@Component
public class ProductValidationService {


    private final ProductServiceClient productServiceClient;

    public ProductValidationService(ProductServiceClient productServiceClient) {
        this.productServiceClient = productServiceClient;
    }

    public ProductDto validateProduct(Long productId) {
        ProductDto product = productServiceClient.getProductById(productId);
        if (product == null) {
            throw new ProductNotFoundException(productId);
        }
        return product;
    }
}
