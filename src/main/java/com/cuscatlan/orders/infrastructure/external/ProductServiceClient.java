package com.cuscatlan.orders.infrastructure.external;

import com.cuscatlan.orders.application.dto.ProductDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign client interface for interacting with the Product Service.
 * <p>
 * This interface defines methods to retrieve product information from
 * the Product Service microservice.
 * </p>
 */
@FeignClient(
        name = "product-service",
        url = "http://localhost:8081/api/v1/products")
@Service
public interface ProductServiceClient {

    /**
     * Retrieves a list of all products.
     *
     * @return a list of ProductDto containing product details
     */
    @GetMapping
    List<ProductDto> getAllProducts();

    /**
     * Retrieves a specific product by its ID.
     *
     * @param id the ID of the product to retrieve
     * @return the ProductDto containing the product details
     */
    @GetMapping("/{id}")
    ProductDto getProductById(@PathVariable("id") Long id);

    /**
     * Retrieves a list of products based on their IDs.
     *
     * @param ids a list of product IDs to retrieve
     * @return a list of ProductDto containing the product details
     */
    @GetMapping("/batch")
    List<ProductDto> getProductsByIds(@RequestParam("ids") List<Long> ids);
}
