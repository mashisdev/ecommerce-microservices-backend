package com.microservice.order.service.impl;

import com.microservice.order.dto.OrderDto;
import com.microservice.order.dto.request.OrderRequest;
import com.microservice.order.entity.Order;
import com.microservice.order.entity.OrderItem;
import com.microservice.order.entity.OrderStatus;
import com.microservice.order.mapper.OrderMapper;
import com.microservice.order.repository.OrderRepository;
import com.microservice.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final WebClient.Builder webClientBuilder;

    String INVENTORY_SERVICE_BASE_URL = "lb://inventory-service/api/inventory";
    private record ConsumeInventoryRequest(
            String sku,
            int quantity
    ) {}

    @Override
    @Transactional
    public Mono<OrderDto> createOrder(OrderRequest request) {
        List<ConsumeInventoryRequest> consumeRequests = request.items().stream()
                .map(item -> new ConsumeInventoryRequest(item.sku(), item.quantity()))
                .toList();
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<OrderDto> getOrderById(UUID orderId) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Page<OrderDto>> getAllOrders(Pageable pageable) {
        return null;
    }

    @Override
    @Transactional
    public Mono<OrderDto> updateOrderStatus(UUID orderId, String newStatus) {
        return null;
    }

    @Override
    @Transactional
    public Mono<Void> deleteOrder(UUID orderId) {
        return null;
    }
}
