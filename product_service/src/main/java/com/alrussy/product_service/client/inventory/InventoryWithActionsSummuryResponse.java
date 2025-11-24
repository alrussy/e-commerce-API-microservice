package com.alrussy.product_service.client.inventory;

public record InventoryWithActionsSummuryResponse(
        String skuCode, Integer openingStock, Integer incoming, Integer outoing, Integer stock) {}
