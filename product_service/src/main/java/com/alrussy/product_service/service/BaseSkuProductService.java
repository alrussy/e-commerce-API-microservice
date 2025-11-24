package com.alrussy.product_service.service;

import java.util.List;
import java.util.Map;

import com.alrussy.product_service.model.dto.PagedResult;
import com.alrussy.product_service.model.dto.sku_product_dto.SkuProductFilter;
import com.alrussy.product_service.model.dto.sku_product_dto.SkuProductRequest;
import com.alrussy.product_service.model.dto.sku_product_dto.SkuProductResponse;

public interface BaseSkuProductService extends BaseService<String, SkuProductResponse, SkuProductRequest> {

    PagedResult<SkuProductResponse> findByIsPrimary(Integer pageNumber, Integer pageSize);

    List<SkuProductResponse> findByValueDetails(Long productId, List<String> values);

    List<SkuProductResponse> findByIds(List<String> skuCodes);

    List<SkuProductResponse> findByProduct(Long productId);

    List<SkuProductResponse> findByCategoryId(Long categoryId);

    Map<Object, List<SkuProductResponse>> findByBrandIds(List<Long> brandIds);

    List<SkuProductResponse> findByBrandId(Long brandId);

    Map<Object, List<SkuProductResponse>> findByDepartmentIds(List<Long> departmentIds);

    List<SkuProductResponse> findByDepartmentId(Long departmentId);

    PagedResult<SkuProductResponse> filter(
            int pageNumber, SkuProductFilter filter, String direction, String... sortedBy);

    PagedResult<SkuProductResponse> findByProductSkuCodesInAndIsPrimary(
            List<String> skuCodes, Integer pageNumber, Integer pageSize);
}
