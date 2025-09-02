package com.microservice.inventory.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ConsumeInventoryRequest(
        @NotNull(message = "Product SKU cannot be null")
        String sku,
        @Min(value = 1, message = "Quantity must be at least 1")
        @Max(value = 999, message = "Quantity cannot exceed 999")
        int quantity
) {}
