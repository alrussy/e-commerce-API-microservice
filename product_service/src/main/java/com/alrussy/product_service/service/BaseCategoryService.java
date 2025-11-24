package com.alrussy.product_service.service;

import java.util.Collection;
import java.util.List;

import com.alrussy.product_service.model.dto.category_dto.CategoryFilter;
import com.alrussy.product_service.model.dto.category_dto.CategoryRequest;
import com.alrussy.product_service.model.dto.category_dto.CategoryResponse;

public interface BaseCategoryService extends BaseService<Long, CategoryResponse, CategoryRequest> {

    Collection<CategoryResponse> findByName(String name);

    List<CategoryResponse> findByGroupId(Long id);

    List<CategoryResponse> findByBrandId(Long brandId);

    Integer deleteDepartment(Long categoryId, Long departmentId);

    Integer deleteNameDetails(Long categoryId, String name);

    List<CategoryResponse> filter(CategoryFilter filter);
}
