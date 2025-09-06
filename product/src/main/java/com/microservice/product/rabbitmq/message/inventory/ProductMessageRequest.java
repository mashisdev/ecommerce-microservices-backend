package com.microservice.product.rabbitmq.message.inventory;

public record ProductMessageRequest(String sku, String eventType, Integer quantity) {}

