package com.cuscatlan.orders.shared.mapper;

import com.cuscatlan.orders.application.dto.OrderItemDto;
import com.cuscatlan.orders.domain.model.OrderItem;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between OrderItem entities and OrderItem DTOs.
 */
@Component
public class OrderItemMapper extends AbstractGenericMapper<OrderItem, OrderItemDto> {

    /**
     * Constructs an OrderItemMapper.
     */
    public OrderItemMapper() {
        super(OrderItem.class, OrderItemDto.class);
    }

}
