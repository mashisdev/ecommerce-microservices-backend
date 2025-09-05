package com.microservice.order.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQJsonProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.topic.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.keys.order}")
    private String orderKey;

    public void sendOrderMessage(Object message) {
        rabbitTemplate.convertAndSend(exchange, orderKey, message);
    }
}
