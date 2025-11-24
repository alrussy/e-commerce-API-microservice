package com.alrussy.product_service.model.dto.brand_dto;

import java.util.List;

import com.alrussy.product_service.model.dto.category_dto.CategoryResponse;

public record BrandResponse(
        Long id, String name, String imageUrl, Integer productCount, List<CategoryResponse> categories) {}
