package product_app.model.dto.product_dto;

import java.util.Map;
import product_app.model.dto.brand_dto.BrandResponse;
import product_app.model.dto.category_dto.CategoryResponse;
import product_app.model.dto.department_dto.DepartmentResponse;

public record ProductResponse(
        Long id,
        String name,
        String imageUrl,
        CategoryResponse category,
        DepartmentResponse department,
        BrandResponse brand,
        Map<?, ?> details,
        String about) {}
