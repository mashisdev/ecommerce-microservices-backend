package com.microservice.inventory.service;

import com.microservice.inventory.entity.Inventory;
import com.microservice.inventory.exception.InsufficientStockException;
import com.microservice.inventory.exception.InventoryNotFoundException;
import com.microservice.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    @Transactional(readOnly = true)
    public Mono<Inventory> getStockBySku(String sku) {
        return inventoryRepository.findBySku(sku)
                .switchIfEmpty(Mono.error(() -> new InventoryNotFoundException("Inventory not found for SKU: " + sku)));
    }

    @Override
    @Transactional
    public Mono<Inventory> consumeInventory(String sku, Integer quantity) {
        return inventoryRepository.findBySku(sku)
                .switchIfEmpty(Mono.error(() -> new InventoryNotFoundException("Inventory not found for SKU: " + sku)))
                .flatMap(inventory -> {
                    if (inventory.getQuantity() < quantity) {
                        return Mono.error(() -> new InsufficientStockException("Insufficient stock for SKU: " + sku));
                    }
                    inventory.setQuantity(inventory.getQuantity() - quantity);
                    return inventoryRepository.save(inventory);
                });
    }

    @Override
    @Transactional
    public Mono<Inventory> updateInventory(String sku, Integer quantity) {
        return inventoryRepository.findBySku(sku)
                .switchIfEmpty(Mono.error(() -> new InventoryNotFoundException("Inventory not found for SKU: " + sku)))
                .flatMap(inventory -> {
                    inventory.setQuantity(quantity);
                    return inventoryRepository.save(inventory);
                });
    }

    @Override
    @Transactional
    public Mono<Void> deleteInventoryBySku(String sku) {
        return inventoryRepository.deleteBySku(sku)
                .switchIfEmpty(Mono.error(() -> new InventoryNotFoundException("Inventory not found for SKU: " + sku)));
    }
}
