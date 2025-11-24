package com.alrussy.product_service.model.dto.brand_dto.projections;

import com.alrussy.product_service.model.dto.brand_dto.BrandResponse;

public interface BrandProjectionGlobal {

    Long getId();

    String getName();

    String getImage_Url();

    Integer getProduct_count();

    default BrandResponse mapToBrandResponse() {
        System.out.println("this brand is : " + getName());
        return new BrandResponse(getId(), getName(), getImage_Url(), getProduct_count(), null);
    }
}
