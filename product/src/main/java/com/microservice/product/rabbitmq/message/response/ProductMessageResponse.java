package com.microservice.product.rabbitmq.message.response;

public record ProductMessageResponse(String sku, String eventType, Integer quantity) {}

