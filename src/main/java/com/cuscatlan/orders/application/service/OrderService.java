package com.cuscatlan.orders.application.service;

import com.cuscatlan.orders.application.dto.OrderRequestDto;
import com.cuscatlan.orders.application.dto.OrderResponseDto;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing orders.
 * Provides methods for creating, retrieving, updating, and deleting orders.
 */
public interface OrderService {

    /**
     * Creates a new order.
     *
     * @param orderDto the order request data
     * @return OrderResponseDto containing the created order information
     */
    OrderResponseDto createOrder(OrderRequestDto orderDto);

    /**
     * Retrieves all orders.
     *
     * @return a list of all order request data
     */
    List<OrderRequestDto> getAllOrders();

    /**
     * Retrieves an order by its ID.
     *
     * @param id the ID of the order to retrieve
     * @return an Optional containing the order data if found, otherwise an empty Optional
     */
    Optional<OrderRequestDto> getOrderById(Long id);

    /**
     * Updates an existing order.
     *
     * @param orderDto the order request data with the updated information
     * @return an Optional containing the updated order information if successful, otherwise an empty Optional
     */
    Optional<OrderResponseDto> updateOrder(OrderRequestDto orderDto);

    /**
     * Deletes an order by its ID.
     *
     * @param id the ID of the order to delete
     * @return OrderResponseDto containing information about the deleted order
     */
    OrderResponseDto deleteOrder(Long id);
}
