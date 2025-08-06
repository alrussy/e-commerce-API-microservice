package com.alrussy_dev.inventory_service.model.dto;

import java.util.List;

public record ActionRequest(String actionId, String description, List<LineProductOfInventory> lineProduct) {}
