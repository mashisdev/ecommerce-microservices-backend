package com.microservice.product.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductDto(
        Long id,
        String name,
        String description,
        boolean active,
        String imageUrl,
        String sku,
        BigDecimal unitPrice,
        Long categoryId,
        Long brandId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
