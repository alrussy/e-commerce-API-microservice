package com.alrussy_dev.inventory_service.model.dto;

public record InventoryWithActionResponse(String skuCode, Integer stockQuantity, ActionForItemResponse action) {}
