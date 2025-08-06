package product_app.mapper;

import product_app.model.dto.sku_product_dto.SkuProductRequest;
import product_app.model.dto.sku_product_dto.SkuProductResponse;
import product_app.model.entities.SkuProduct;

public interface SkuProductMapper extends BaseMapper<SkuProduct, SkuProductResponse, SkuProductRequest> {

    SkuProduct toEntity(SkuProductRequest request, boolean isPrimary);

    SkuProductResponse fromEntityOutPrduct(SkuProduct entity);
}
