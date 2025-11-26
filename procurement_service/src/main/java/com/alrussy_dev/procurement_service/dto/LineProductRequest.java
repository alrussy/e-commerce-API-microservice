package com.alrussy_dev.procurement_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record LineProductRequest(
        @NotBlank String skuCode,
        String currency,
        @Min(1) Double price,
        @Min(0) Double discount,
        @Min(1) Integer quantity,
        String unit) {}
