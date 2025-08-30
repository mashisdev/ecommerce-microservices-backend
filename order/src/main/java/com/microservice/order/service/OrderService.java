package com.microservice.order.service;

import com.microservice.order.dto.OrderDto;
import com.microservice.order.dto.request.OrderRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface OrderService {
    OrderDto createOrder(OrderRequest request);
    OrderDto getOrderById(UUID orderId);
    Mono<Page<OrderDto>> getAllOrders(Pageable pageable);
    Mono<OrderDto> updateOrderStatus(UUID orderId, String newStatus);
    Mono<Void> deleteOrder(UUID orderId);
}
