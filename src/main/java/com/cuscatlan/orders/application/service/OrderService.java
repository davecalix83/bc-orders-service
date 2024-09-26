package com.cuscatlan.orders.application.service;

import com.cuscatlan.orders.application.dto.OrderRequestDto;
import com.cuscatlan.orders.application.dto.OrderResponseDto;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    OrderResponseDto createOrder(OrderRequestDto orderDto);

    List<OrderRequestDto> getAllOrders();

    Optional<OrderRequestDto> getOrderById(Long id);

    Optional<OrderResponseDto> updateOrder(OrderRequestDto orderDto);

    OrderResponseDto deleteOrder(Long id);
}
