package com.alrussy_dev.order_service.queries.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record LineProductRequest(
        @NotBlank String skuCode, @Min(1) Double price, @Min(0) Double discount, @Min(1) Integer quentity) {}
