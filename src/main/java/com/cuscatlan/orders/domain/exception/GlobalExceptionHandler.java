package com.cuscatlan.orders.domain.exception;

import com.cuscatlan.orders.application.dto.OrderResponseDto;
import java.time.LocalDateTime;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for the application.
 * This class intercepts exceptions thrown by controllers and 
 * constructs appropriate error responses to be sent back to the client.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles NoOrdersFoundException and returns a 404 response.
     *
     * @param ex the exception instance
     * @return ResponseEntity containing the error response
     */
    @ExceptionHandler(NoOrdersFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoOrdersFoundException(NoOrdersFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("ERROR");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setProcessedAt(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles OrderNotFoundException and returns a 404 response.
     *
     * @param ex the exception instance
     * @return ResponseEntity containing the error response
     */
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOrderNotFoundException(OrderNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("ERROR");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setProcessedAt(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles UpdateOrderException and returns a 500 response.
     *
     * @param ex the exception instance
     * @return ResponseEntity containing the error response
     */
    @ExceptionHandler(UpdateOrderException.class)
    public ResponseEntity<ErrorResponse> handleOrderUpdateException(UpdateOrderException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("ERROR");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setProcessedAt(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles OrderDeletionException and returns a 500 response.
     *
     * @param ex the exception instance
     * @return ResponseEntity containing the error response
     */
    @ExceptionHandler(OrderDeletionException.class)
    public ResponseEntity<ErrorResponse> handleOrderDeletionException(OrderDeletionException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("ERROR");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setProcessedAt(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles ProductNotFoundException and returns a 404 response.
     *
     * @param ex the exception instance
     * @return ResponseEntity containing the error response
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("ERROR");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setProcessedAt(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles general exceptions and returns a 500 response.
     *
     * @param ex the exception instance
     * @return ResponseEntity containing the error response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<OrderResponseDto> handleGeneralException(@NotNull Exception ex) {
        OrderResponseDto response = buildErrorResponse("An unexpected error occurred: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * Builds an OrderResponseDto for error responses.
     *
     * @param message the error message
     * @return OrderResponseDto containing error details
     */
    private OrderResponseDto buildErrorResponse(String message) {
        return OrderResponseDto.builder()
                .orderId(null)
                .status("ERROR")
                .message(message)
                .processedAt(LocalDateTime.now())
                .build();
    }
}
