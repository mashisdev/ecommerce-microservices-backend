package com.microservice.order.rabbitmq.message.request;

import com.microservice.order.dto.request.OrderItemRequest;

import java.util.List;

public record OrderMessage(
        String orderTrackingNumber,
        List<OrderItemRequest> items
) {}
