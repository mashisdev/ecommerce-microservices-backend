package com.microservice.order.repository;

import com.microservice.order.entity.OrderItem;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface OrderItemRepository extends R2dbcRepository<OrderItem, Long> {
}