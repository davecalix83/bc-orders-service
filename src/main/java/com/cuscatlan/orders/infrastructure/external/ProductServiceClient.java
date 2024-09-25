package com.cuscatlan.orders.infrastructure.external;

import com.cuscatlan.orders.application.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "product-service",
        url = "http://localhost:8081/api/v1/products")
@Service
public interface ProductServiceClient {

    @GetMapping
    List<ProductDto> getAllProducts();

    @GetMapping("/{id}")
    ProductDto getProductById(@PathVariable("id") Long id);

    @GetMapping("/batch")
    List<ProductDto> getProductsByIds(@RequestParam("ids") List<Long> ids);
}
