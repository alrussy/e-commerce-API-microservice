package com.alrussy.product_service.model.dto.brand_dto;

import java.util.List;

public record BrandRequest(String name, String imageUrl, List<Long> categoryIds) {}
