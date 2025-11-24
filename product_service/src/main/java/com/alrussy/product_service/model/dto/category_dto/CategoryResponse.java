package com.alrussy.product_service.model.dto.category_dto;

import com.alrussy.product_service.model.dto.brand_dto.BrandResponse;
import com.alrussy.product_service.model.dto.department_dto.DepartmentResponse;
import com.alrussy.product_service.model.dto.details_dto.NameDetailsResponse;
import com.alrussy.product_service.model.dto.group_category_dto.GroupCategoryResponse;
import java.util.List;

public record CategoryResponse(
        Long id,
        String name,
        String imageUrl,
        GroupCategoryResponse group,
        List<BrandResponse> brands,
        List<DepartmentResponse> departments,
        List<NameDetailsResponse> nameDetail) {}
