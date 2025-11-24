package com.alrussy_dev.inventory_service.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record LineProductOfInventory(@NotBlank String skuCode, @Min(1) Integer quantity, String unit) {}
