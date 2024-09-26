package com.cuscatlan.orders.shared.mapper;

import com.cuscatlan.orders.application.dto.OrderRequestDto;
import com.cuscatlan.orders.domain.model.Order;
import org.springframework.stereotype.Component;

/**
 *
 * @author hguzman
 */
@Component
public class OrderMapper extends AbstractGenericMapper<Order, OrderRequestDto> {

    public OrderMapper() {
        super(Order.class, OrderRequestDto.class);
    }
    
}
