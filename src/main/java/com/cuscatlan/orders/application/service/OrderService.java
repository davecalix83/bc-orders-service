package com.cuscatlan.orders.application.service;

import com.cuscatlan.orders.application.dto.OrderDto;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    OrderDto createOrder(OrderDto orderDto);

    List<OrderDto> getAllOrders();

    Optional<OrderDto> getOrderById(Long id);

    Optional<OrderDto> updateOrder(OrderDto orderDto);

    void deleteOrder(Long id);
}
