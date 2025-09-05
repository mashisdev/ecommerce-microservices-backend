package com.microservice.order.rabbitmq.listener;

import com.microservice.order.rabbitmq.message.response.ProductResponse;
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
public class OrderMessageListener {

    private final OrderService orderService;
    private final OrderItemRepository orderItemRepository;

    @RabbitListener(queues = "${spring.rabbitmq.queues.order.response}")
    public Mono<Void> receiveProductData(ProductResponse message) {
        log.info("Received product data for order: {}", message.orderTrackingNumber());

        return orderService.createOrder(message)
                .doOnError(throwable -> log.error("Error creating order {}: {}", message.orderTrackingNumber(), throwable.getMessage()))
                .onErrorResume(throwable -> Mono.empty())
                .then();
    }
}
