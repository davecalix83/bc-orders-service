package com.cuscatlan.orders.application.service;

import com.cuscatlan.orders.application.dto.*;
import com.cuscatlan.orders.domain.exception.*;
import com.cuscatlan.orders.domain.model.Order;
import com.cuscatlan.orders.domain.model.OrderItem;
import com.cuscatlan.orders.infrastructure.repository.OrderRepository;
import com.cuscatlan.orders.shared.mapper.OrderItemMapper;
import com.cuscatlan.orders.shared.mapper.OrderMapper;
import com.cuscatlan.orders.shared.utils.Common;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

/**
 * Implementation of the OrderService interface.
 * Handles business logic related to order management.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private static final String NO_ORDERS_FOUND_MESSAGE = "No order records found.";
    private static final String ORDER_CREATION_SUCCESS_MESSAGE = "Order created successfully";

    private final OrderRepository orderRepository;
    private final ProductValidationService productValidationService;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final Common utils;

    /**
     * Creates a new order based on the provided order request data.
     *
     * @param orderRequestDto the order request data
     * @return OrderResponseDto containing the created order information
     */
    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        Order order = orderMapper.toEntity(orderRequestDto);
        processOrderItems(order, orderRequestDto.getItems());
        order.setStatus("PENDING");
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(utils.calculateTotalAmount(order.getItems()));
        Order savedOrder = saveOrder(order);
        return buildOrderResponse(savedOrder.getId(), ORDER_CREATION_SUCCESS_MESSAGE);
    }

    /**
     * Retrieves all orders.
     *
     * @return a list of all order request data
     * @throws NoOrdersFoundException if no orders are found
     */
    @Override
    public List<OrderRequestDto> getAllOrders() {
        List<OrderRequestDto> orders = orderRepository.findAll().stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());

        if (orders.isEmpty()) {
            throw new NoOrdersFoundException(NO_ORDERS_FOUND_MESSAGE);
        }

        return orders;
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param id the ID of the order to retrieve
     * @return an Optional containing the order data if found, otherwise an empty Optional
     * @throws OrderNotFoundException if the order with the given ID is not found
     */
    @Override
    public Optional<OrderRequestDto> getOrderById(Long id) {
        return Optional.ofNullable(orderRepository.findById(id)
                .map(orderMapper::toDto)
                .orElseThrow(() -> new OrderNotFoundException(id)));
    }

    /**
     * Updates an existing order based on the provided order request data.
     *
     * @param orderRequestDto the order request data with updated information
     * @return an Optional containing the updated order information if successful, otherwise an empty Optional
     * @throws OrderNotFoundException if the order to be updated is not found
     */
    @Override
    public Optional<OrderResponseDto> updateOrder(@NotNull OrderRequestDto orderRequestDto) {
        Order existingOrder = orderRepository.findById(orderRequestDto.getId())
                .orElseThrow(() -> new OrderNotFoundException(orderRequestDto.getId()));

        updateOrderDetails(existingOrder, orderRequestDto);
        processOrderItems(existingOrder, orderRequestDto.getItems());

        existingOrder.setOrderDate(LocalDateTime.now());
        existingOrder.setTotalAmount(utils.calculateTotalAmount(existingOrder.getItems()));
        existingOrder.setStatus(existingOrder.getStatus());
        Order updatedOrder = saveOrder(existingOrder);

        return Optional.of(buildOrderResponse(updatedOrder.getId(), "Order updated successfully"));
    }

    /**
     * Deletes an order by its ID.
     *
     * @param id the ID of the order to delete
     * @return OrderResponseDto containing information about the deleted order
     * @throws OrderNotFoundException if the order with the given ID does not exist
     * @throws OrderDeletionException if the deletion fails
     */
    @Override
    public OrderResponseDto deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException(id);
        }

        try {
            orderRepository.deleteById(id);
            return utils.buildOrderResponse(id, "OK", "Order deleted successfully");
        } catch (Exception e) {
            throw new OrderDeletionException("Failed to delete order with ID: " + id + ". Reason: " + e.getMessage());
        }
    }

    /**
     * Processes order items and associates them with the order.
     *
     * @param order           the order to which items will be added
     * @param orderItemDtos   the list of order item data transfer objects
     */
    private void processOrderItems(@NotNull Order order, @NotNull List<OrderItemDto> orderItemDtos) {
        order.getItems().clear();

        orderItemDtos.forEach(orderItemDto -> {
            ProductDto product = productValidationService.validateProduct(orderItemDto.getProductId());
            OrderItem orderItem = orderItemMapper.toEntity(orderItemDto);
            orderItem.setPrice(product.getPrice());
            orderItem.setOrder(order);
            order.getItems().add(orderItem);
        });
    }

    /**
     * Updates the details of an existing order.
     *
     * @param existingOrder       the order to be updated
     * @param orderRequestDto     the new order request data
     */
    private void updateOrderDetails(@NotNull Order existingOrder, @NotNull OrderRequestDto orderRequestDto) {
        existingOrder.setCustomerId(orderRequestDto.getCustomerId());
        existingOrder.setOrderDate(orderRequestDto.getOrderDate());
        existingOrder.setStatus(orderRequestDto.getStatus());
        existingOrder.setTotalAmount(orderRequestDto.getTotalAmount());
    }

    /**
     * Saves the given order to the repository.
     *
     * @param order the order to be saved
     * @return the saved order
     */
    private @NotNull Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    /**
     * Builds an order response with a given order ID and message.
     *
     * @param orderId the ID of the order
     * @param message the message to include in the response
     * @return an OrderResponseDto containing the order ID and message
     */
    private OrderResponseDto buildOrderResponse(Long orderId, String message) {
        return utils.buildOrderResponse(orderId, "OK", message);
    }
}
