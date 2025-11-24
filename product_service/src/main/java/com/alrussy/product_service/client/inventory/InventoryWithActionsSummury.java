package com.alrussy.product_service.client.inventory;

public record InventoryWithActionsSummury(
        String skuCode, Integer openingStock, Integer incoming, Integer outoing, Integer stock) {}
