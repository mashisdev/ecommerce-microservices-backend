package com.microservice.inventory.service;

import com.microservice.inventory.entity.Inventory;
import com.microservice.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    public Mono<Integer> getStockBySku(String sku) {
        return inventoryRepository.findBySku(sku)
                .map(Inventory::getQuantity).defaultIfEmpty(0);
    }

    public Mono<Inventory> updateInventory(String sku, Integer quantity) {
        return inventoryRepository.findBySku(sku)
                .flatMap(inventory -> {
                    inventory.setQuantity(quantity);
                    return inventoryRepository.save(inventory);
                })
                .switchIfEmpty(Mono.error(() -> new RuntimeException("Inventory not found for SKU: " + sku)));
    }

    public Mono<Void> deleteInventoryBySku(String sku) {
        return inventoryRepository.deleteBySku(sku)
                .switchIfEmpty(Mono.error(()-> new RuntimeException("Inventory not found for SKU: " + sku)));
    }
}
