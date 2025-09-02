package com.microservice.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {

    private Long id;
    private String sku;
    private Integer quantity;

    // Auditing
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
