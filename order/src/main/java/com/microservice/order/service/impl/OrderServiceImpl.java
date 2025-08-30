package com.microservice.order.service.impl;

import com.microservice.order.dto.OrderDto;
import com.microservice.order.dto.request.OrderRequest;
import com.microservice.order.entity.Order;
import com.microservice.order.entity.OrderStatus;
import com.microservice.order.mapper.OrderMapper;
import com.microservice.order.repository.OrderRepository;
import com.microservice.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderDto createOrder(OrderRequest request) {
        return null;
    }

    @Override
    public OrderDto getOrderById(UUID orderId) {
        return null;
    }

    @Override
    public Mono<Page<OrderDto>> getAllOrders(Pageable pageable) {
        return null;
    }

    @Override
    public Mono<OrderDto> updateOrderStatus(UUID orderId, String newStatus) {
        return null;
    }

    @Override
    public Mono<Void> deleteOrder(UUID orderId) {
        return null;
    }
}
