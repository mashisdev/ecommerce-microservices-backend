package com.microservice.product.rabbitmq.message.order;

public record OrderItemRequest(String sku, int quantity) {}

