package com.microservice.inventory.repository;

import com.microservice.inventory.entity.Inventory;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface InventoryRepository extends R2dbcRepository<Inventory, Long> {

    Mono<Inventory> findBySku(String sku);
    Mono<Void> deleteBySku(String sku);
}
