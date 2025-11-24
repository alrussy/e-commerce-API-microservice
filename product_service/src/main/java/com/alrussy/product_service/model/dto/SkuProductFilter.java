package com.alrussy.product_service.model.dto;

public record SkuProductFilter(
        String SkuCod,
        String productId,
        Long groupId,
        Long categoryId,
        Long brandId,
        Long departmentId,
        Double price) {}
