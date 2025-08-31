package com.microservice.product.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "products")
public class Product {

    @Id
    private Long id;
    private String name;
    private String description;
    private boolean active;
    @Column("image_url")
    private String imageUrl;
    private String sku;
    @Column("unit_price")
    private BigDecimal unitPrice;

    // Auditing
    @Column("created_at")
    @CreatedDate
    private LocalDateTime createdAt;
    @Column("updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    // Relations 1-n
    @Column("category_id")
    private Long categoryId;
    @Column("brand_id")
    private Long brandId;
}