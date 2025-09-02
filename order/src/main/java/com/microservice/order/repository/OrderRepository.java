package com.microservice.order.repository;

import com.microservice.order.entity.Order;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface OrderRepository extends R2dbcRepository<Order, UUID> {

    Mono<Order> findByOrderTrackingNumber(String orderTrackingNumber);
}
