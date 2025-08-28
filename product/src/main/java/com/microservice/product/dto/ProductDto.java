package com.microservice.product.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductDto(
        Long id,
        String name,
        String description,
        boolean active,
        String imageUrl,
        int stock,
        BigDecimal unitPrice,
        Long category,
        Long brand,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate
) {}
