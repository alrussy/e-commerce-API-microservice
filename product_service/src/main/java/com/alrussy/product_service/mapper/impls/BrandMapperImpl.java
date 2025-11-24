package com.alrussy.product_service.mapper.impls;

import com.alrussy.product_service.mapper.BrandMapper;
import com.alrussy.product_service.mapper.CategoryMapper;
import com.alrussy.product_service.model.dto.brand_dto.BrandRequest;
import com.alrussy.product_service.model.dto.brand_dto.BrandResponse;
import com.alrussy.product_service.model.entities.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BrandMapperImpl implements BrandMapper {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Brand toEntity(BrandRequest request) {
        return Brand.builder().name(request.name()).imageUrl(request.imageUrl()).build();
    }

    @Override
    public BrandResponse fromEntity(Brand entity) {

        return new BrandResponse(
                entity.getId(),
                entity.getName(),
                entity.getImageUrl(),
                null,
                entity.getBrandCategory() == null
                        ? null
                        : entity.getBrandCategory().stream()
                                .map(category ->
                                        categoryMapper.fromEntityOutBrandAndDepartments(category.getCategory()))
                                .toList());
    }

    @Override
    public BrandResponse fromEntityOutCategory(Brand entity) {
        return new BrandResponse(entity.getId(), entity.getName(), entity.getImageUrl(), null, null);
    }

    @Override
    public BrandResponse fromEntityWithProductCount(Brand entity, int countProduct) {
        return new BrandResponse(entity.getId(), entity.getName(), entity.getImageUrl(), countProduct, null);
    }
}
