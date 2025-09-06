package com.microservice.product.rabbitmq.listener;

import com.microservice.product.rabbitmq.message.order.OrderItemRequest;
import com.microservice.product.rabbitmq.message.order.OrderMessageRequest;
import com.microservice.product.rabbitmq.message.order.ProductItem;
import com.microservice.product.rabbitmq.message.order.ProductMessageResponse;
import com.microservice.product.service.ProductService;
import com.microservice.product.rabbitmq.RabbitMQJsonProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductMessageListener {

    private final ProductService productService;
    private final RabbitMQJsonProducer rabbitMQJsonProducer;

    @RabbitListener(queues = "${spring.rabbitmq.queues.order.request}")
    public void receiveOrderRequest(OrderMessageRequest message) {
        log.info("Received message from order service for order: {}", message.orderTrackingNumber());

        Flux.fromIterable(message.items())
                .flatMap(this::getProductDetails)
                .collectList()
                .flatMap(productItems -> {
                    ProductMessageResponse response = new ProductMessageResponse(message.orderTrackingNumber(), productItems);
                    rabbitMQJsonProducer.sendOrderMessage(response);
                    return Mono.empty();
                })
                .subscribe();
    }

    private Mono<ProductItem> getProductDetails(OrderItemRequest item) {
        return productService.getProductBySku(item.sku())
                .map(product -> new ProductItem(product.sku(), item.quantity(), product.unitPrice()));
    }

}