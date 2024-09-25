package com.cuscatlan.orders.application.service;

import com.cuscatlan.orders.application.dto.OrderDto;
import com.cuscatlan.orders.application.dto.OrderItemDto;
import com.cuscatlan.orders.application.dto.ProductDto;
import com.cuscatlan.orders.domain.model.Order;
import com.cuscatlan.orders.domain.model.OrderItem;
import com.cuscatlan.orders.domain.repository.OrderRepository;
import com.cuscatlan.orders.infrastructure.external.ProductServiceClient;
import com.cuscatlan.orders.shared.mapper.OrderItemMapper;
import com.cuscatlan.orders.shared.mapper.OrderMapper;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductServiceClient productServiceClient;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderDto createOrder(OrderDto orderDto) {

        Order order = orderMapper.toEntity(orderDto);
        order.getItems().clear();

        for (OrderItemDto orderItemDto : orderDto.getItems()) {
            Long productId = orderItemDto.getProductId();

            ProductDto product = productServiceClient.getProductById(productId);

            if (product != null) {
                OrderItem orderItem = orderItemMapper.toEntity(orderItemDto);
                orderItem.setOrder(order);
                order.getItems().add(orderItem);
            } else {
                throw new RuntimeException("Product not found: " + productId);
            }
        }

        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OrderDto> getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDto);
    }

    @Override
    public Optional<OrderDto> updateOrder(OrderDto orderDto) {
        return orderRepository.findById(orderDto.getId()).map(existingOrder -> {
            existingOrder.setCustomerId(orderDto.getCustomerId());
            existingOrder.setOrderDate(orderDto.getOrderDate());
            existingOrder.setStatus(orderDto.getStatus());
            existingOrder.setTotalAmount(orderDto.getTotalAmount());

            // Crear una nueva lista de items
            List<OrderItem> newItems = new ArrayList<>();

            for (OrderItemDto orderItemDto : orderDto.getItems()) {
                OrderItem orderItem = orderItemMapper.toEntity(orderItemDto);
                orderItem.setOrder(existingOrder); // Establecer la relación bidireccional
                newItems.add(orderItem);
            }

            // Aquí se eliminan los items que no están en la nueva lista
            existingOrder.getItems().retainAll(newItems); // Mantiene solo los elementos que están en la nueva lista
            existingOrder.getItems().forEach(item -> item.setOrder(existingOrder)); // Actualiza la relación

            existingOrder.getItems().addAll(newItems); // Agrega los nuevos items

            // Guardar la orden actualizada
            Order updatedOrder = orderRepository.save(existingOrder);
            return orderMapper.toDto(updatedOrder);
        });
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
