package com.alrussy_dev.inventory_service.model.dto;

import com.alrussy_dev.inventory_service.model.enums.ActionType;
import java.util.List;

public record ActionRequest(
        String actionId, ActionType type, String description, List<LineProductOfInventory> lineProducts) {}
