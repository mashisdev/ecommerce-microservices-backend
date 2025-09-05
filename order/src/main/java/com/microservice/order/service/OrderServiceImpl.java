package com.microservice.order.service;

import com.microservice.order.dto.OrderDto;
import com.microservice.order.dto.OrderItemDto;
import com.microservice.order.dto.message.OrderMessage;
import com.microservice.order.dto.message.ProductResponse;
import com.microservice.order.dto.request.OrderItemRequest;
import com.microservice.order.dto.request.OrderRequest;
import com.microservice.order.dto.response.InventoryResponse;
import com.microservice.order.entity.Order;
import com.microservice.order.entity.OrderItem;
import com.microservice.order.entity.OrderStatus;
import com.microservice.order.exception.ErrorMessage;
import com.microservice.order.exception.InsufficientStockException;
import com.microservice.order.exception.InventoryNotFoundException;
import com.microservice.order.mapper.OrderMapper;
import com.microservice.order.rabbitmq.RabbitMQJsonProducer;
import com.microservice.order.repository.OrderItemRepository;
import com.microservice.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final WebClient.Builder webClientBuilder;
    private final RabbitMQJsonProducer rabbitMQJsonProducer;

    @Value("${spring.webclient.inventory.url}")
    private String INVENTORY_SERVICE_URL;

    @Override
    @Transactional
    public Mono<String> placeOrder(OrderRequest request) {
        return consumeInventory(request.items())
                .then(Mono.defer(() -> {
                    String orderTrackingNumber = UUID.randomUUID().toString();
                    OrderMessage orderMessage = new OrderMessage(orderTrackingNumber, request.items());
                    rabbitMQJsonProducer.sendOrderMessage(orderMessage);
                    return Mono.just(orderTrackingNumber);
                }));
    }

    public Mono<Void> consumeInventory(List<OrderItemRequest> items) {
        return webClientBuilder.build().post()
                .uri(INVENTORY_SERVICE_URL)
                .bodyValue(items)
                .retrieve()
                .onStatus(status -> status.equals(HttpStatus.NOT_FOUND), response -> {
                    return response.bodyToMono(String.class)
                            .flatMap(errorMessage -> Mono.error(new InventoryNotFoundException(errorMessage)));
                })
                .onStatus(status -> status.equals(HttpStatus.CONFLICT), response -> {
                    return response.bodyToMono(String.class)
                            .flatMap(errorMessage -> Mono.error(new InsufficientStockException(errorMessage)));
                })
                .toBodilessEntity()
                .then();
    }

    @Override
    @Transactional
    public Mono<Void> createOrder(ProductResponse response) {
        Order newOrder = new Order();
        newOrder.setOrderTrackingNumber(response.orderTrackingNumber());
        newOrder.setStatus(OrderStatus.PENDING);

        Set<OrderItem> orderItems = response.productItems().stream()
                .map(item -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setSku(item.sku());
                    orderItem.setUnitPrice(item.unitPrice());
                    orderItem.setQuantity(item.quantity());
                    return orderItem;
                })
                .collect(Collectors.toSet());

        BigDecimal totalPrice = orderItems.stream()
                .map(item -> item.getUnitPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalQuantity = orderItems.stream()
                .mapToInt(OrderItem::getQuantity)
                .sum();

        newOrder.setTotalPrice(totalPrice);
        newOrder.setTotalQuantity(totalQuantity);

        return orderRepository.save(newOrder)
                .flatMap(savedOrder -> {
                    orderItems.forEach(item -> item.setOrderId(savedOrder.getId()));
                    return orderItemRepository.saveAll(orderItems)
                            .then(Mono.just(savedOrder));
                })
                .then();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<OrderDto> getOrderById(UUID orderId) {
        return orderRepository.findById(orderId)
                .map(orderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<OrderDto> getOrderByOrderTrackingNumber(String orderTrackingNumber) {
        return orderRepository.findByOrderTrackingNumber(orderTrackingNumber)
                .map(orderMapper::toDto);
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
