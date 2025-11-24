package com.alrussy.product_service.service;

import com.alrussy.product_service.model.dto.group_category_dto.GroupCategoryRequest;
import com.alrussy.product_service.model.dto.group_category_dto.GroupCategoryResponse;
import com.alrussy.product_service.model.dto.group_category_dto.GroupFilter;
import java.util.List;

public interface BaseGroupCategoryService extends BaseService<Long, GroupCategoryResponse, GroupCategoryRequest> {

    List<GroupCategoryResponse> findByName(String name);

    List<GroupCategoryResponse> filter(GroupFilter filter);
}
