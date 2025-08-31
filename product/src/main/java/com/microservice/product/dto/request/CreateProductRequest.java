package com.microservice.product.dto.request;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;

public record CreateProductRequest(
        @NotBlank(message = "Product name is required")
        @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
        String name,

        @Size(max = 500, message = "Description must not exceed 500 characters")
        String description,

        @URL(message = "Invalid image URL format")
        @Size(max = 255, message = "Image URL must not exceed 255 characters")
        String imageUrl,

        @NotBlank(message = "SKU is required")
        @Size(max = 50, message = "SKU must not exceed 50 characters")
        @Pattern(regexp = "^[a-zA-Z0-9-]{1,50}$", message = "SKU must contain only letters, numbers, and hyphens")
        String sku,

        @NotNull(message = "Unit price is required")
        @DecimalMin(value = "0.01", message = "Unit price must be greater than or equal to 0.01")
        BigDecimal unitPrice,

        @NotNull(message = "Category ID cannot be null")
        Long categoryId,

        @NotNull(message = "Brand ID cannot be null")
        Long brandId
) {}
