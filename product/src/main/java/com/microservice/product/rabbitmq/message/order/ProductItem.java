package com.microservice.product.rabbitmq.message.order;

import java.math.BigDecimal;

public record ProductItem(
        String sku,
        int quantity,
        BigDecimal unitPrice
) {}
