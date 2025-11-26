package com.alrussy_dev.procurement_service.mapper.impl;

import com.alrussy_dev.procurement_service.dto.LineProductRequest;
import com.alrussy_dev.procurement_service.dto.LineProductResponse;
import com.alrussy_dev.procurement_service.entity.LineProduct;
import com.alrussy_dev.procurement_service.mapper.LineProductMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class LineProductMapperImpl implements LineProductMapper {
    @Override
    public LineProduct toEntity(LineProductRequest request) {
        return LineProduct.builder()
                .curruncy(request.currency())
                .discount(request.discount())
                .price(request.price())
                .quantity(request.quantity())
                .skuCode(request.skuCode())
                .unit(request.unit())
                .build();
    }

    @Override
    public LineProductResponse fromEntity(LineProduct entity) {

        var priceAfterDiscount = entity.getPrice() - entity.getPrice() * entity.getDiscount() / 100;
        var totalLine = priceAfterDiscount * entity.getQuantity();
        return new LineProductResponse(
                entity.getSkuCode(),
                entity.getPrice(),
                entity.getDiscount(),
                entity.getCurruncy(),
                priceAfterDiscount,
                entity.getQuantity(),
                totalLine);
    }
}
