package com.microservice.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
        @NotBlank(message = "Category name cannot be blank")
        @Size(min = 3, max = 50, message = "Category name must be between 3 and 50 characters")
        @Pattern(regexp = "^[a-zA-Z0-9\\s&.'-]+$", message = "Category name can only contain letters, numbers, spaces, and special characters (&.'-)")
        String name
) {}
