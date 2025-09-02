package com.microservice.order.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue productDataRequestQueue() {
        return new Queue("product-data-response-queue", true);
    }
}
