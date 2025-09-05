package com.microservice.order.rabbitmq.message.response;

import java.math.BigDecimal;

public record ProductItem(
        String sku,
        int quantity,
        BigDecimal unitPrice
) {}
