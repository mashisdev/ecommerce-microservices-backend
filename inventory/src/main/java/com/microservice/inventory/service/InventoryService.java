package com.microservice.inventory.service;

import com.microservice.inventory.entity.Inventory;
import reactor.core.publisher.Mono;

public interface InventoryService {

    Mono<Inventory> getStockBySku(String sku);
    Mono<Inventory> consumeInventory(String sku, Integer quantity);
    Mono<Inventory> updateInventory(String sku, Integer quantity);
    Mono<Void> deleteInventoryBySku(String sku);
}
