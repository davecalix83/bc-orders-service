package com.cuscatlan.orders.domain.exception;

import com.cuscatlan.orders.application.dto.OrderResponseDto;
import java.time.LocalDateTime;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoOrdersFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoOrdersFoundException(NoOrdersFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("ERROR");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setProcessedAt(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOrderNotFoundException(OrderNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("ERROR");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setProcessedAt(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UpdateOrderException.class)
    public ResponseEntity<ErrorResponse> handleOrderUpdateException(UpdateOrderException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("ERROR");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setProcessedAt(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(OrderDeletionException.class)
    public ResponseEntity<ErrorResponse> handleOrderDeletionException(OrderDeletionException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("ERROR");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setProcessedAt(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("ERROR");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setProcessedAt(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
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
