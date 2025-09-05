package com.microservice.order.service;

import com.microservice.order.dto.OrderDto;
import com.microservice.order.rabbitmq.message.response.ProductResponse;
import com.microservice.order.dto.request.OrderRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface OrderService {
    Mono<String> placeOrder(OrderRequest request);
    Mono<Void> createOrder(ProductResponse response);
    Mono<OrderDto> getOrderById(UUID orderId);
    Mono<OrderDto> getOrderByOrderTrackingNumber(String orderTrackingNumber);
    Mono<Page<OrderDto>> getAllOrders(Pageable pageable);
    Mono<OrderDto> updateOrderStatus(UUID orderId, String newStatus);
    Mono<Void> deleteOrder(UUID orderId);
}
