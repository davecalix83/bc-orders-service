package com.cuscatlan.orders.application.controller;

import com.cuscatlan.orders.application.dto.OrderRequestDto;
import com.cuscatlan.orders.application.dto.OrderResponseDto;
import com.cuscatlan.orders.application.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing orders.
 * Provides endpoints for creating, retrieving, updating, and deleting orders.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    /**
     * Creates a new order.
     *
     * @param orderRequestDto the order request data
     * @return ResponseEntity containing the created order and HTTP status 201 (Created)
     */
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        OrderResponseDto createdOrder = orderService.createOrder(orderRequestDto);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    /**
     * Retrieves all orders.
     *
     * @return ResponseEntity containing a list of orders and HTTP status 200 (OK)
     */
    @GetMapping
    public ResponseEntity<List<OrderRequestDto>> getAllOrders() {
        List<OrderRequestDto> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param id the ID of the order to retrieve
     * @return ResponseEntity containing the order if found, otherwise HTTP status 404 (Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderRequestDto> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(orderDto -> new ResponseEntity<>(orderDto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Updates an existing order.
     *
     * @param orderRequestDto the order request data with the ID of the order to update
     * @return ResponseEntity containing the updated order if successful, otherwise HTTP status 404 (Not Found) or 400 (Bad Request)
     */
    @PutMapping()
    public ResponseEntity<OrderResponseDto> updateOrder(@RequestBody OrderRequestDto orderRequestDto) {
        if (orderRequestDto.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return orderService.updateOrder(orderRequestDto)
                .map(updatedOrder -> new ResponseEntity<>(updatedOrder, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Deletes an order by its ID.
     *
     * @param id the ID of the order to delete
     * @return ResponseEntity with HTTP status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
