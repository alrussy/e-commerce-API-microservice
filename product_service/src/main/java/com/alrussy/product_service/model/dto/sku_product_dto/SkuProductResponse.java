package com.alrussy.product_service.model.dto.sku_product_dto;

import com.alrussy.product_service.model.dto.product_dto.ProductResponse;
import java.util.List;
import java.util.Map;

public record SkuProductResponse(
        String skuCode,
        ProductResponse product,
        Map<String, String> details,
        List<String> imageUrls,
        Double price,
        Double discount,
        String currency,
        Double priceAfterDiscount,
        String unit,
        Integer stock,
        Integer incoming,
        Integer outoing,
        Integer openingStock) {}
