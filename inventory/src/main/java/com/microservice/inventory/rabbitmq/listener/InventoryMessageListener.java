package com.microservice.inventory.rabbitmq.listener;

import com.microservice.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryMessageListener {

    private final InventoryService inventoryService;

    public record ProductMessageResponse(String sku, String eventType, Integer quantity) {}

    @RabbitListener(queues = "inventory-update-queue")
    public void handleProductEvent(ProductMessageResponse message) {
        log.info("Received event: {} for SKU: {}", message.eventType(), message.sku());

        if ("CREATE".equals(message.eventType())) {
            log.info("Creating inventory for SKU: {} with quantity: {}", message.sku(), message.quantity());
            inventoryService.createInventory(message.sku(), message.quantity())
                    .subscribe(
                            inventory -> log.info("Successfully created inventory for SKU: {}", inventory.getSku()),
                            error -> log.error("Error creating inventory for SKU {}: {}", message.sku(), error.getMessage())
                    );
        } else if ("DELETE".equals(message.eventType())) {
            log.info("Deleting inventory for SKU: {}", message.sku());
            inventoryService.deleteInventoryBySku(message.sku())
                    .doOnSuccess(aVoid -> log.info("Successfully deleted inventory for SKU: {}", message.sku()))
                    .doOnError(error -> log.error("Error deleting inventory for SKU {}: {}", message.sku(), error.getMessage()))
                    .subscribe();
        }
    }
}
