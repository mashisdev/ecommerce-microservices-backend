package com.microservice.order.event;

import com.microservice.order.entity.OrderStatus;

import java.math.BigDecimal;

public record OrderEvent(
        String orderTrackingNumber,
        int totalQuantity,
        BigDecimal totalPrice,
        OrderStatus status) {
}
