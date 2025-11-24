package com.alrussy_dev.inventory_service.model.dto;

public record InventoryWithActionsSummuryResponse(
        String skuCode, Integer openingStock, Integer incoming, Integer outoing, Integer stock) {}
