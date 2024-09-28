package com.cuscatlan.orders.shared.utils;

import com.cuscatlan.orders.application.dto.OrderResponseDto;
import com.cuscatlan.orders.domain.model.OrderItem;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

/**
 * Utility class providing common functionalities for order processing.
 */
@Service
public class Common {

    /**
     * Builds an OrderResponse DTO.
     *
     * @param orderId the ID of the order
     * @param status the status of the order
     * @param message a message related to the order
     * @return an OrderResponse DTO with the provided details
     */
    public OrderResponseDto buildOrderResponse(Long orderId, String status, String message) {
        return OrderResponseDto.builder()
                .orderId(orderId)
                .status(status)
                .message(message)
                .processedAt(LocalDateTime.now())
                .build();
    }

    /**
     * Calculates the total amount of the given list of order items.
     *
     * @param items a list of OrderItem
     * @return the total amount as a BigDecimal
     */
    public BigDecimal calculateTotalAmount(@NotNull List<OrderItem> items) {
        return items.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
