package com.cuscatlan.orders.shared.utils;

import com.cuscatlan.orders.application.dto.OrderResponseDto;
import com.cuscatlan.orders.domain.model.OrderItem;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class Common {

    public OrderResponseDto buildOrderResponse(Long orderId, String status, String message) {
        return OrderResponseDto.builder()
                .orderId(orderId)
                .status(status)
                .message(message)
                .processedAt(LocalDateTime.now())
                .build();
    }

    public BigDecimal calculateTotalAmount(@NotNull List<OrderItem> items) {
        return items.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
