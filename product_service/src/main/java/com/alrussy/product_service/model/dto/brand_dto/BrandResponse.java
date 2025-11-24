package com.alrussy.product_service.model.dto.brand_dto;

import com.alrussy.product_service.model.dto.category_dto.CategoryResponse;
import java.util.List;

public record BrandResponse(
        Long id, String name, String imageUrl, Integer productCount, List<CategoryResponse> categories) {}
