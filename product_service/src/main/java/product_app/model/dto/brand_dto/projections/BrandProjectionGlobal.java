package product_app.model.dto.brand_dto.projections;

import product_app.model.dto.brand_dto.BrandResponse;

public interface BrandProjectionGlobal {

    Long getId();

    String getName();

    String getImage_Url();

    Boolean getIs_Feature();

    Integer getProduct_count();

    default BrandResponse mapToBrandResponse() {
        return new BrandResponse(getId(), getName(), getImage_Url(), getProduct_count(), null);
    }
}
