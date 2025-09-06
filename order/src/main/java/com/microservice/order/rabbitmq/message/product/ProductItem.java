package com.microservice.order.rabbitmq.message.product;

import java.math.BigDecimal;

public record ProductItem(
        String sku,
        int quantity,
        BigDecimal unitPrice
) {}
