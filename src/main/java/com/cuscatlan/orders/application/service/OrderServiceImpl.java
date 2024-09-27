package com.cuscatlan.orders.application.service;

import com.cuscatlan.orders.application.dto.*;
import com.cuscatlan.orders.domain.exception.NoOrdersFoundException;
import com.cuscatlan.orders.domain.exception.OrderDeletionException;
import com.cuscatlan.orders.domain.exception.OrderNotFoundException;
import com.cuscatlan.orders.domain.exception.ProductNotFoundException;
import com.cuscatlan.orders.domain.exception.UpdateOrderException;
import com.cuscatlan.orders.domain.model.Order;
import com.cuscatlan.orders.domain.model.OrderItem;
import com.cuscatlan.orders.infrastructure.external.ProductServiceClient;
import com.cuscatlan.orders.infrastructure.repository.OrderRepository;
import com.cuscatlan.orders.shared.mapper.OrderItemMapper;
import com.cuscatlan.orders.shared.mapper.OrderMapper;
import com.cuscatlan.orders.shared.utils.Common;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private static final String NO_ORDERS_FOUND_MESSAGE = "No order records found.";
    private static final String ORDER_CREATION_SUCCESS_MESSAGE = "Order created successfully";

    private final OrderRepository orderRepository;
    private final ProductServiceClient productServiceClient;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final Common common;

    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        Order order = orderMapper.toEntity(orderRequestDto);
        processOrderItems(order, orderRequestDto.getItems());
        order.setStatus("PENDING");
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(calculateTotalAmount(order.getItems())); // Cambiado
        Order savedOrder = orderRepository.save(order);

        return common.buildOrderResponse(
                savedOrder.getId(),
                "OK",
                ORDER_CREATION_SUCCESS_MESSAGE
        );
    }

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

    @Override
    public Optional<OrderRequestDto> getOrderById(Long id) {
        return Optional.ofNullable(orderRepository.findById(id)
                .map(orderMapper::toDto)
                .orElseThrow(() -> new OrderNotFoundException(id)));
    }

    @Override
    public Optional<OrderResponseDto> updateOrder(OrderRequestDto orderRequestDto) {
        Order existingOrder = orderRepository.findById(orderRequestDto.getId())
                .orElseThrow(() -> new OrderNotFoundException(orderRequestDto.getId()));

        try {
            updateOrderDetails(existingOrder, orderRequestDto);
            processOrderItems(existingOrder, orderRequestDto.getItems());
            existingOrder.setOrderDate(LocalDateTime.now());
            existingOrder.setTotalAmount(calculateTotalAmount(existingOrder.getItems()));
            existingOrder.setStatus(existingOrder.getStatus());
            Order updatedOrder = orderRepository.save(existingOrder);
            return Optional.of(
                    common.buildOrderResponse(updatedOrder.getId(),
                            "OK",
                            "Order updated successfully")
            );
        } catch (Exception e) {
            throw new UpdateOrderException("Failed to update order: " + e.getMessage());
        }
    }

    @Override
    public OrderResponseDto deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException(id);
        }
        try {
            orderRepository.deleteById(id);
            return common.buildOrderResponse(
                    id,
                    "OK",
                    "Order deleted successfully"
            );
        } catch (Exception e) {
            throw new OrderDeletionException("Failed to delete order with ID: " + id + ". Reason: " + e.getMessage());
        }
    }

    private void processOrderItems(@NotNull Order order, @NotNull List<OrderItemDto> orderItemDtos) {
        order.getItems().clear();
        orderItemDtos.forEach(orderItemDto -> {
            ProductDto product = validateProduct(orderItemDto.getProductId());
            OrderItem orderItem = orderItemMapper.toEntity(orderItemDto);
            orderItem.setPrice(product.getPrice());
            orderItem.setOrder(order);
            order.getItems().add(orderItem);
        });
    }

    private ProductDto validateProduct(Long productId) {
        ProductDto product = productServiceClient.getProductById(productId);
        if (product == null) {
            throw new ProductNotFoundException(productId);
        }
        return product;
    }

    private void updateOrderDetails(@NotNull Order existingOrder, @NotNull OrderRequestDto orderRequestDto) {
        existingOrder.setCustomerId(orderRequestDto.getCustomerId());
        existingOrder.setOrderDate(orderRequestDto.getOrderDate());
        existingOrder.setStatus(orderRequestDto.getStatus());
        existingOrder.setTotalAmount(orderRequestDto.getTotalAmount());
    }

  
    private BigDecimal calculateTotalAmount(List<OrderItem> items) {
        return items.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))) 
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
