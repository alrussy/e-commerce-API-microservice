package com.alrussy.product_service.model.dto.sku_product_dto;

public record SkuProductFilter(
        String skuCode,
        Long productId,
        Long groupId,
        Long categoryId,
        Long brandId,
        Long departmentId,
        Double priceGreateThan,
        Double priceLessThan,
        Double priceLessThanOrEquel,
        Double priceGreateThanOrEquel,
        Double priceEquel) {}
