package com.microservice.product.rabbitmq.message.request;

public record OrderItemRequest(String sku, int quantity) {}

