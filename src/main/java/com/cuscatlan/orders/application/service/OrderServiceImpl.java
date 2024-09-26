package com.cuscatlan.orders.application.service;

import com.cuscatlan.orders.application.dto.*;
import com.cuscatlan.orders.domain.exception.OrderNotFoundException;
import com.cuscatlan.orders.domain.exception.ProductNotFoundException;
import com.cuscatlan.orders.domain.model.Order;
import com.cuscatlan.orders.domain.model.OrderItem;
import com.cuscatlan.orders.infrastructure.repository.OrderRepository;
import com.cuscatlan.orders.infrastructure.external.ProductServiceClient;
import com.cuscatlan.orders.shared.mapper.OrderItemMapper;
import com.cuscatlan.orders.shared.mapper.OrderMapper;
import com.cuscatlan.orders.shared.utils.Common;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductServiceClient productServiceClient;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final Common common;

    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        Order order = orderMapper.toEntity(orderRequestDto);
        processOrderItems(order, orderRequestDto.getItems());
        Order savedOrder = orderRepository.save(order);
        return common.buildOrderResponse(
                savedOrder.getId()
                , "OK"
                , "Order created successfully"
        );
    }

    @Override
    public List<OrderRequestDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
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

        updateOrderDetails(existingOrder, orderRequestDto);
        processOrderItems(existingOrder, orderRequestDto.getItems());
        Order updatedOrder = orderRepository.save(existingOrder);
        return Optional.of(
                common.buildOrderResponse(updatedOrder.getId()
                        , "OK"
                        , "Order updated successfully")
        );
    }

    @Override
    public OrderResponseDto deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException(id);
        }
        orderRepository.deleteById(id);
        return common.buildOrderResponse(
                id
                , "OK"
                , "Order deleted successfully"
        );
    }

    private void processOrderItems(@NotNull Order order, @NotNull List<OrderItemDto> orderItemDtos) {
        order.getItems().clear();
        orderItemDtos.forEach(orderItemDto -> {
            validateProduct(orderItemDto.getProductId());
            OrderItem orderItem = orderItemMapper.toEntity(orderItemDto);
            orderItem.setOrder(order);
            order.getItems().add(orderItem);
        });
    }

    private void validateProduct(Long productId) {
        ProductDto product = productServiceClient.getProductById(productId);
        if (product == null) {
            throw new ProductNotFoundException(productId);
        }
    }

    private void updateOrderDetails(@NotNull Order existingOrder, @NotNull OrderRequestDto orderRequestDto) {
        existingOrder.setCustomerId(orderRequestDto.getCustomerId());
        existingOrder.setOrderDate(orderRequestDto.getOrderDate());
        existingOrder.setStatus(orderRequestDto.getStatus());
        existingOrder.setTotalAmount(orderRequestDto.getTotalAmount());
    }
}