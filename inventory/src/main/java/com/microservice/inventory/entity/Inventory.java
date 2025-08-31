package com.microservice.inventory.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "inventory")
public class Inventory {

    @Id
    private Long id;
    private String sku;
    private Long quantity;
}
