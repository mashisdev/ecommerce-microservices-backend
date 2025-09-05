package com.microservice.product.listener;

import com.microservice.product.message.request.OrderItemRequest;
import com.microservice.product.message.request.OrderMessage;
import com.microservice.product.service.ProductService;
import com.microservice.product.service.RabbitMQJsonProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductMessageListener {

    private final ProductService productService;
    private final RabbitMQJsonProducer rabbitMQJsonProducer;

    @RabbitListener(queues = "${spring.rabbitmq.queues.order.request}")
    public void receiveOrderRequest(OrderMessage message) {
        log.info("Received message from order service for order: {}", message.orderTrackingNumber());

        Flux.fromIterable(message.items())
                .flatMap(this::getProductDetails)
                .collectList()
                .flatMap(productItems -> {
                    OrderResponse response = new OrderResponse(message.orderTrackingNumber(), productItems);
                    rabbitMQJsonProducer.sendOrderMessage(response);
                    return Mono.empty();
                })
                .subscribe();
    }

    private Mono<ProductItem> getProductDetails(OrderItemRequest item) {
        return productService.getProductBySku(item.sku())
                .map(product -> new ProductItem(product.sku(), item.quantity(), product.unitPrice()));
    }

    private record OrderResponse(String orderTrackingNumber, List<ProductItem> productItems) {}
    private record ProductItem(String sku, int quantity, BigDecimal unitPrice) {}
}