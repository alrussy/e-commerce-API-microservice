package com.alrussy.product_service.mapper.impls;

import com.alrussy.product_service.mapper.GroupCategoryMapper;
import com.alrussy.product_service.model.dto.group_category_dto.GroupCategoryRequest;
import com.alrussy.product_service.model.dto.group_category_dto.GroupCategoryResponse;
import com.alrussy.product_service.model.entities.GroupCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GroupCategoryMapperImpl implements GroupCategoryMapper {

    @Override
    public GroupCategory toEntity(GroupCategoryRequest request) {

        return GroupCategory.builder()
                .name(request.name())
                .imageUrl(request.imageUrl())
                .build();
    }

    @Override
    public GroupCategoryResponse fromEntity(GroupCategory entity) {
        return new GroupCategoryResponse(entity.getId(), entity.getName(), entity.getImageUrl());
    }
}
