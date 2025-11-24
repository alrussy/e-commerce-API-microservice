package com.alrussy.product_service.mapper;

import com.alrussy.product_service.model.dto.brand_dto.BrandRequest;
import com.alrussy.product_service.model.dto.brand_dto.BrandResponse;
import com.alrussy.product_service.model.entities.Brand;

public interface BrandMapper extends BaseMapper<Brand, BrandResponse, BrandRequest> {

    BrandResponse fromEntityWithProductCount(Brand entity, int countProduct);

    BrandResponse fromEntityOutCategory(Brand entity);
}
