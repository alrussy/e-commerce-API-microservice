package com.alrussy.product_service.mapper;

import com.alrussy.product_service.model.dto.category_dto.CategoryRequest;
import com.alrussy.product_service.model.dto.category_dto.CategoryResponse;
import com.alrussy.product_service.model.entities.Category;

public interface CategoryMapper extends BaseMapper<Category, CategoryResponse, CategoryRequest> {

    CategoryResponse fromEntityOutBrandAndDepartments(Category entity);
}
