package com.alrussy.product_service.service;

import java.util.List;

import com.alrussy.product_service.model.dto.brand_dto.BrandFilter;
import com.alrussy.product_service.model.dto.brand_dto.BrandRequest;
import com.alrussy.product_service.model.dto.brand_dto.BrandResponse;

public interface BaseBrandService extends BaseService<Long, BrandResponse, BrandRequest> {

    List<BrandResponse> findByCategory(Long id);

    List<BrandResponse> findByName(String name);

    List<BrandResponse> findAllWithProductCount();

    BrandResponse findByIdWithProductCount(Long id);

    List<BrandResponse> findByCategoryWithProductCount(Long id);

    List<BrandResponse> findByNameAndCategoryId(String name, Long categoryId);

    List<BrandResponse> filter(BrandFilter filter);
}
