package com.microservice.product.message.response;

public record ProductMessageResponse(String sku, String eventType, Integer quantity) {}

