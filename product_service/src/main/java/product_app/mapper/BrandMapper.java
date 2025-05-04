package product_app.mapper;

import product_app.model.dto.brand_dto.BrandRequest;
import product_app.model.dto.brand_dto.BrandResponse;
import product_app.model.entities.Brand;

public class BrandMapper {

    public static Brand toEntity(BrandRequest request) {
        return Brand.builder()
                .name(request.name())
                .imageUrl(request.imageUrl())
                .isFeature(request.isFeature())
                .build();
    }

    public static BrandResponse fromEntity(Brand entity) {
        return null;
    }
}
