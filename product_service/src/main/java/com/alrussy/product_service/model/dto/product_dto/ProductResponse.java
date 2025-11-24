package com.alrussy.product_service.model.dto.product_dto;

import com.alrussy.product_service.model.dto.brand_dto.BrandResponse;
import com.alrussy.product_service.model.dto.category_dto.CategoryResponse;
import com.alrussy.product_service.model.dto.department_dto.DepartmentResponse;
import com.alrussy.product_service.model.dto.group_category_dto.GroupCategoryResponse;
import com.alrussy.product_service.model.dto.unit_dto.UnitResponse;
import java.util.Map;

public record ProductResponse(
        Long id,
        String name,
        String skuCode,
        String imageUrl,
        GroupCategoryResponse group,
        CategoryResponse category,
        DepartmentResponse department,
        BrandResponse brand,
        Map<?, ?> details,
        String about,
        UnitResponse unit,
        String currency,
        Double price,
        Double discount,
        Double priceAfterDiscoun) {}
