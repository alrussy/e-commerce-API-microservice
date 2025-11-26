package com.alrussy_dev.procurement_service.client;

import java.util.List;

public record ActionRequest(
        String actionId, ActionType type, String description, List<LineProductOfInventory> lineProducts) {}
