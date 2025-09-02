package com.microservice.order.dto.message;

import com.microservice.order.dto.request.OrderItemRequest;

import java.util.List;

public record OrderMessage(
        String orderId,
        List<OrderItemRequest> items
) {}
