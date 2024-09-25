package com.cuscatlan.orders.shared.mapper;

import com.cuscatlan.orders.application.dto.OrderItemDto;
import com.cuscatlan.orders.domain.model.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper extends AbstractGenericMapper<OrderItem, OrderItemDto> {

    public OrderItemMapper() {
        super(OrderItem.class, OrderItemDto.class);
    }

}
