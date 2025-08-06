package com.alrussy_dev.inventory_service.model.dto;

import com.alrussy_dev.inventory_service.model.enums.ActionType;
import java.time.LocalDateTime;
import java.util.List;

public record ActionResponse(
        String actionId,
        LocalDateTime actionDate,
        ActionType type,
        String description,
        List<LineProductOfInventory> lineProduct) {}
