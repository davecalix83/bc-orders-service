package com.cuscatlan.orders.infrastructure.repository;

import com.cuscatlan.orders.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for accessing Order entities.
 * <p>
 * This interface extends JpaRepository, providing methods for CRUD operations 
 * on Order entities, as well as the ability to query the database.
 * </p>
 */
public interface OrderRepository extends JpaRepository<Order, Long> {}
