package com.microservice.product.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Getter
@Setter
@Table(name = "products")
public class Product {

    @Id
    private Long id;
    private String name;
    private String description;
    private boolean active;
    private String imageUrl;
    private int stock;
    private BigDecimal unitPrice;

//    private Category category;
//    private Brand brand;
}

