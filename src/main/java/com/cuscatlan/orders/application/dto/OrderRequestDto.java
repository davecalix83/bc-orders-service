package com.cuscatlan.orders.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {

    @Schema(description = "Identifier of the order", defaultValue = "1")
    private Long id;

    @Schema(description = "Identifier of the customer", defaultValue = "1")
    private Long customerId;

    @Schema(description = "Date of the order", defaultValue = "2023-01-01T00:00:00")
    private LocalDateTime orderDate;

    @Schema(description = "Status of the order", defaultValue = "PENDING")
    private String status;

    @Schema(description = "Total amount of the order", defaultValue = "0.00")
    private BigDecimal totalAmount;

    @Schema(description = "List of order items")
    private List<OrderItemDto> items;
}
