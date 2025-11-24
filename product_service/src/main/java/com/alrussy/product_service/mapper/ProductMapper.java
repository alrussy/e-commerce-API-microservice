package com.alrussy.product_service.mapper;

import com.alrussy.product_service.model.dto.product_dto.ProductRequest;
import com.alrussy.product_service.model.dto.product_dto.ProductResponse;
import com.alrussy.product_service.model.entities.Product;

public interface ProductMapper extends BaseMapper<Product, ProductResponse, ProductRequest> {

    ProductResponse fromEntityWithBrand(Product entity);

    ProductResponse fromEntityOutCategory(Product entity);

    ProductResponse fromEntityOutDetails(Product entity);
}
