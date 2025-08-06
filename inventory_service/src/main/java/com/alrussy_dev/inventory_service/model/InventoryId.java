package com.alrussy_dev.inventory_service.model;

import jakarta.persistence.Embeddable;

@Embeddable
public record InventoryId(String actionId, String skuCode) {}
