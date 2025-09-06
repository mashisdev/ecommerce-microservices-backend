package com.microservice.order.rabbitmq.message.product;

import com.microservice.order.dto.request.OrderItemRequest;

import java.util.List;

public record OrderMessageRequest(
        String orderTrackingNumber,
        List<OrderItemRequest> items
) {}
