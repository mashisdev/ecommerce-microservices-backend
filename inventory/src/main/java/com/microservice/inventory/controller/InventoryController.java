package com.microservice.inventory.controller;

import com.microservice.inventory.entity.Inventory;
import com.microservice.inventory.service.InventoryService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{sku}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Integer> getStockBySku(@PathVariable @NotBlank String sku) {
        return inventoryService.getStockBySku(sku);
    }

    @PutMapping("/{sku}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Inventory> updateInventory(@PathVariable @NotBlank String sku, @RequestBody @Min(0) int quantity) {
        return inventoryService.updateInventory(sku, quantity);
    }

    @DeleteMapping("/{sku}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteInventoryBySku(@PathVariable @NotBlank String sku) {
        return inventoryService.deleteInventoryBySku(sku);
    }
}

