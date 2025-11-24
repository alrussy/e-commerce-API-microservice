package com.alrussy_dev.inventory_service.model.dto;

import java.util.List;

public record InventoryWithActionsResponse(
        String skuCode,
        Integer openingStock,
        Integer incoming,
        Integer outoing,
        Integer stock,
        List<ActionForItemResponse> actions) {}
