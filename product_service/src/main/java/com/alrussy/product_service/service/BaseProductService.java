package com.alrussy.product_service.service;

import java.util.List;
import java.util.Map;

import com.alrussy.product_service.model.dto.PagedResult;
import com.alrussy.product_service.model.dto.product_dto.ProductFilter;
import com.alrussy.product_service.model.dto.product_dto.ProductRequest;
import com.alrussy.product_service.model.dto.product_dto.ProductResponse;

public interface BaseProductService extends BaseService<Long, ProductResponse, ProductRequest> {

    List<ProductResponse> findByBrandId(Long bransId);

    Map<Object, List<ProductResponse>> findByDepartmentIds(List<String> departmentIds);

    List<ProductResponse> findByName(String name);

    List<ProductResponse> findByCategoryId(Long categoryId);

    List<ProductResponse> findByBrandIdAndName(Long brandId, String name);

    List<ProductResponse> findByCategoryIdAnName(Long categoryId, String name);

    PagedResult<ProductResponse> filter(int pageNumber, ProductFilter filter, String direction, String... sortedBy);

    List<ProductResponse> filter(ProductFilter filter);

    List<ProductResponse> findBySkuCode(String skuCode);
}
