package com.microservice.inventory.service;

import com.microservice.inventory.entity.Inventory;
import reactor.core.publisher.Mono;

public interface InventoryService {

    Mono<Integer> getStockBySku(String sku);
    Mono<Inventory> updateInventory(String sku, Integer quantity);
    Mono<Void> deleteInventoryBySku(String sku);
}
