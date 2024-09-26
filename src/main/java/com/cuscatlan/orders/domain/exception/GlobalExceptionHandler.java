package com.cuscatlan.orders.domain.exception;

import com.cuscatlan.orders.application.dto.OrderResponseDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<OrderResponseDto> handleOrderNotFound(@NotNull OrderNotFoundException ex) {
        OrderResponseDto response = buildErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<OrderResponseDto> handleProductNotFound(@NotNull ProductNotFoundException ex) {
        OrderResponseDto response = buildErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<OrderResponseDto> handleGeneralException(@NotNull Exception ex) {
        OrderResponseDto response = buildErrorResponse("An unexpected error occurred: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    private OrderResponseDto buildErrorResponse(String message) {
        return OrderResponseDto.builder()
                .orderId(null)
                .status("ERROR")
                .message(message)
                .processedAt(LocalDateTime.now())
                .build();
    }
}