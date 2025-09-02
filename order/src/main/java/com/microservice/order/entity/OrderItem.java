package com.microservice.order.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Table("order_item")
public class OrderItem {

    @Id
    private Long id;

    private String sku;

    @Column("unit_price")
    private BigDecimal unitPrice;

    private int quantity;

    @Column("order_id")
    private UUID orderId;
}
