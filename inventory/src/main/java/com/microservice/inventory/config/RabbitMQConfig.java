package com.microservice.inventory.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue inventoryUpdateQueue() {
        return new Queue("inventory-update-queue", true);
    }
}
