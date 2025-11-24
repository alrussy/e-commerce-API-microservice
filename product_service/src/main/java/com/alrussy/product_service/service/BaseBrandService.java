package com.alrussy.product_service.service;

import com.alrussy.product_service.model.dto.brand_dto.BrandFilter;
import com.alrussy.product_service.model.dto.brand_dto.BrandRequest;
import com.alrussy.product_service.model.dto.brand_dto.BrandResponse;
import java.util.List;

public interface BaseBrandService extends BaseService<Long, BrandResponse, BrandRequest> {

    List<BrandResponse> findByCategory(Long id);

    List<BrandResponse> findByName(String name);

    List<BrandResponse> findAllWithProductCount();

    BrandResponse findByIdWithProductCount(Long id);

    List<BrandResponse> findByCategoryWithProductCount(Long id);

    List<BrandResponse> findByNameAndCategoryId(String name, Long categoryId);

    List<BrandResponse> filter(BrandFilter filter);
}
