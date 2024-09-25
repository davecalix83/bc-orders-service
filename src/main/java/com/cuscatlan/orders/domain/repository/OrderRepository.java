package com.cuscatlan.orders.domain.repository;

import com.cuscatlan.orders.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {}
