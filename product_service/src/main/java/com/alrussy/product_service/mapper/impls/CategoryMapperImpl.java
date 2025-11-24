package com.alrussy.product_service.mapper.impls;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alrussy.product_service.mapper.BrandMapper;
import com.alrussy.product_service.mapper.CategoryMapper;
import com.alrussy.product_service.mapper.DepartmentMapper;
import com.alrussy.product_service.model.dto.category_dto.CategoryRequest;
import com.alrussy.product_service.model.dto.category_dto.CategoryResponse;
import com.alrussy.product_service.model.dto.details_dto.NameDetailsResponse;
import com.alrussy.product_service.model.entities.Category;
import com.alrussy.product_service.model.entities.GroupCategory;

@Component
@Getter
public class CategoryMapperImpl implements CategoryMapper {
    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private DepartmentMapper departmentsMapper;

    @Override
    public Category toEntity(CategoryRequest request) {

        return Category.builder()
                .name(request.name())
                .imageUrl(request.imageUrl())
                .groupCategory(GroupCategory.builder().id(request.groupId()).build())
                .build();
    }

    @Override
    public CategoryResponse fromEntity(Category entity) {
        return new CategoryResponse(
                entity.getId(),
                entity.getName(),
                entity.getImageUrl(),
                new GroupCategoryMapperImpl().fromEntity(entity.getGroupCategory()),
                entity.getBrandCategory() == null || entity.getBrandCategory().isEmpty()
                        ? null
                        : entity.getBrandCategory().stream()
                                .map(bc -> brandMapper.fromEntityOutCategory(bc.getBrand()))
                                .toList(),
                entity.getDepartments() != null
                        ? entity.getDepartments().stream()
                                .map(departmentsMapper::fromEntityOutCategory)
                                .toList()
                        : null,
                entity.getNameDetailsCategory().stream()
                        .map(t -> new NameDetailsResponse(t.getId().getDetailNameId(), null))
                        .toList());
    }

    @Override
    public CategoryResponse fromEntityOutBrandAndDepartments(Category entity) {
        return new CategoryResponse(
                entity.getId(),
                entity.getName(),
                entity.getImageUrl(),
                new GroupCategoryMapperImpl().fromEntity(entity.getGroupCategory()),
                null,
                null,
                entity.getNameDetailsCategory().stream()
                        .map(t -> new NameDetailsResponse(t.getId().getDetailNameId(), null))
                        .toList());
    }
}
