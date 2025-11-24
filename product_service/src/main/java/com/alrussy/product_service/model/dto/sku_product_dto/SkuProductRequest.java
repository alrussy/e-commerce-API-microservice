package com.alrussy.product_service.model.dto.sku_product_dto;

import com.alrussy.product_service.model.dto.details_dto.SkuProductDetailsRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record SkuProductRequest(
        @NotNull Long productId,
        @NotNull Long categoryId,
        @NotNull String skuCodePublic,
        @NotNull Double price,
        @NotNull Double discount,
        @NotNull String currency,
        @NotNull @NotEmpty List<String> imageUrls,
        @NotEmpty @NotNull List<SkuProductDetailsRequest> details,
        Integer openingStock) {}
