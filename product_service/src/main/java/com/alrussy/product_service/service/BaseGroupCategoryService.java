package com.alrussy.product_service.service;

import java.util.List;

import com.alrussy.product_service.model.dto.group_category_dto.GroupCategoryRequest;
import com.alrussy.product_service.model.dto.group_category_dto.GroupCategoryResponse;
import com.alrussy.product_service.model.dto.group_category_dto.GroupFilter;

public interface BaseGroupCategoryService extends BaseService<Long, GroupCategoryResponse, GroupCategoryRequest> {

    List<GroupCategoryResponse> findByName(String name);

    List<GroupCategoryResponse> filter(GroupFilter filter);
}
