package com.microservice.product.rabbitmq.message.order;

import java.util.List;

public record OrderMessageRequest(String orderTrackingNumber, List<OrderItemRequest> items) {}

