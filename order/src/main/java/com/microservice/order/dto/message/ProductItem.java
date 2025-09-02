package com.microservice.order.dto.message;

import java.math.BigDecimal;

public record ProductItem(
        String sku,
        int quantity,
        BigDecimal unitPrice
){}
