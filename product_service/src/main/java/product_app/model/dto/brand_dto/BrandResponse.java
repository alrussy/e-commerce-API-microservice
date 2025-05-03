package product_app.model.dto.brand_dto;

import java.util.List;
import product_app.model.dto.category_dto.CategoryResponse;

public record BrandResponse(
        Long id,
        String name,
        String imageUrl,
        Boolean isFeature,
        Integer productCount,
        List<CategoryResponse> categories) {}
