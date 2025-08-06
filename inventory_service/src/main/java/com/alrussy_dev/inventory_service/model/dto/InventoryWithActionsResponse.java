package com.alrussy_dev.inventory_service.model.dto;

import java.util.List;

public record InventoryWithActionsResponse(
        String skuCode, Integer stockQuantity, List<ActionForItemResponse> actions) {}
