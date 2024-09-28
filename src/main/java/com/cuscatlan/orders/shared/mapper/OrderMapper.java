package com.cuscatlan.orders.shared.mapper;

import com.cuscatlan.orders.application.dto.OrderRequestDto;
import com.cuscatlan.orders.domain.model.Order;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Order entities and OrderRequest DTOs.
 */
@Component
public class OrderMapper extends AbstractGenericMapper<Order, OrderRequestDto> {

    /**
     * Constructs an OrderMapper.
     */
    public OrderMapper() {
        super(Order.class, OrderRequestDto.class);
    }

}
