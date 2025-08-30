package com.microservice.order.dto;

import com.microservice.order.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private UUID id;
    private String orderTrackingNumber;
    private int totalQuantity;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private UUID userId;
    private List<OrderItemDto> orderItems;
}
