package com.alrussy.product_service.model.dto.category_dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

import com.alrussy.product_service.model.dto.department_dto.DepartmentRequest;

public record CategoryRequest(
        String name,
        String imageUrl,
        List<DepartmentRequest> departments,
        List<String> namesDetails,
        @NotNull Long groupId) {}
