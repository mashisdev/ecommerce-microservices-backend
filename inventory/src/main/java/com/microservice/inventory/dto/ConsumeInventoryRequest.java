package com.microservice.inventory.dto;

public record ConsumeInventoryRequest(String sku, int quantity) {
}
