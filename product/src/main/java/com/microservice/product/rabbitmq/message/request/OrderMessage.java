package com.microservice.product.rabbitmq.message.request;

import java.util.List;

public record OrderMessage(String orderTrackingNumber, List<OrderItemRequest> items) {}

