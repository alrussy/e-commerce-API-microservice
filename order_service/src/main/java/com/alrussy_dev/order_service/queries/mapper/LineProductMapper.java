package com.alrussy_dev.order_service.queries.mapper;

import com.alrussy_dev.order_service.client.product.model.SkuProduct;
import com.alrussy_dev.order_service.queries.model.LineProduct;
import com.alrussy_dev.order_service.queries.model.dto.LineProductRequest;
import com.alrussy_dev.order_service.queries.model.dto.LineProductResponse;

public interface LineProductMapper extends BaseMapper<LineProduct, LineProductResponse, LineProductRequest> {

    LineProductResponse fromEntityWithSkuProduct(LineProduct entity, SkuProduct skuProduct);
}
