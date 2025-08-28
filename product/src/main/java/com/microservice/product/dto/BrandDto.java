package com.microservice.product.dto;

import java.time.LocalDateTime;

public record BrandDto(
        Long id,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
