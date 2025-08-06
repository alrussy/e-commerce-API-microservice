package product_app.mapper;

import product_app.model.dto.brand_dto.BrandRequest;
import product_app.model.dto.brand_dto.BrandResponse;
import product_app.model.entities.Brand;

public interface BrandMapper extends BaseMapper<Brand, BrandResponse, BrandRequest> {

    BrandResponse fromEntityWithProductCount(Brand entity, int countProduct);

    BrandResponse fromEntityOutCategory(Brand entity);
}
