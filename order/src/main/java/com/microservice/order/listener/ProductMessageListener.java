package com.microservice.order.listener;

import com.microservice.order.dto.OrderItemDto;
import com.microservice.order.dto.message.ProductResponse;
import com.microservice.order.entity.OrderItem;
import com.microservice.order.entity.OrderStatus;
import com.microservice.order.repository.OrderItemRepository;
import com.microservice.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductMessageListener {

    private final OrderService orderService;
    private final OrderItemRepository orderItemRepository;

    private static final String PRODUCT_RESPONSE_QUEUE = "product-data-response-queue";

    @RabbitListener(queues = PRODUCT_RESPONSE_QUEUE)
    public Mono<Void> receiveProductData(ProductResponse message) {
        log.info("Received product data for order: {}", message.orderTrackingNumber());

        return orderService.createOrder(message)
                .doOnError(throwable -> log.error("Error creating order {}: {}", message.orderTrackingNumber(), throwable.getMessage()))
                .onErrorResume(throwable -> Mono.empty())
                .then();
    }
}
