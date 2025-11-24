package com.alrussy.product_service.mapper;

import com.alrussy.product_service.model.dto.sku_product_dto.SkuProductRequest;
import com.alrussy.product_service.model.dto.sku_product_dto.SkuProductResponse;
import com.alrussy.product_service.model.entities.SkuProduct;

public interface SkuProductMapper extends BaseMapper<SkuProduct, SkuProductResponse, SkuProductRequest> {

    SkuProduct toEntity(SkuProductRequest request, String skuCode, boolean isPrimary);

    SkuProductResponse fromEntityOutPrduct(SkuProduct entity);

    SkuProductResponse fromEntityOutPrduct(SkuProduct entity, Integer quantity);

    SkuProductResponse fromEntityWithStock(SkuProduct entity, Integer stock);

    SkuProductResponse fromEntityWithActionSummury(
            SkuProduct entity, Integer openingStock, Integer incoming, Integer outoing, Integer stock);
}
