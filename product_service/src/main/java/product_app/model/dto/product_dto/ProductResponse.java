package product_app.model.dto.product_dto;

import product_app.model.dto.brand_dto.BrandResponse;
import product_app.model.dto.category_dto.CategoryResponse;
import product_app.model.dto.department_dto.DepartmentResponse;

public record ProductResponse(
        Long id,
        String name,
        Boolean isFeature,
        String imageUrl,
        CategoryResponse category,
        DepartmentResponse department,
        BrandResponse brand,
        String about) {}
