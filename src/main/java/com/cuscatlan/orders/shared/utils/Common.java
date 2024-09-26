package com.cuscatlan.orders.shared.utils;

import com.cuscatlan.orders.application.dto.OrderResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
}
