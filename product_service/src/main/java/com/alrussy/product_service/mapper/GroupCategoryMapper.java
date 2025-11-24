package com.alrussy.product_service.mapper;

import com.alrussy.product_service.model.dto.group_category_dto.GroupCategoryRequest;
import com.alrussy.product_service.model.dto.group_category_dto.GroupCategoryResponse;
import com.alrussy.product_service.model.entities.GroupCategory;

public interface GroupCategoryMapper extends BaseMapper<GroupCategory, GroupCategoryResponse, GroupCategoryRequest> {}
