package com.cuscatlan.orders.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItemDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identifier of the order item", example = "1")
    private Long id;

    @Schema(description = "Identifier of the product", defaultValue = "1")
    private Long productId;

    @Schema(description = "Quantity of the product", defaultValue = "1")
    private Integer quantity;

    @Schema(description = "Price of the product", defaultValue = "0.00")
    private BigDecimal price;
}
