package com.alrussy_dev.inventory_service.model.dto;

import com.alrussy_dev.inventory_service.model.enums.ActionType;
import java.time.LocalDateTime;

public record ActionForItemResponse(
        String actionId, LocalDateTime actionDate, int quantity, ActionType type, String description) {}
