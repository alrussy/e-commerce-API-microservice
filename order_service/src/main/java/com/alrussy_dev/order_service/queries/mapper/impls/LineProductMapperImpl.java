package com.alrussy_dev.order_service.queries.mapper.impls;

import com.alrussy_dev.order_service.client.product.model.SkuProduct;
import com.alrussy_dev.order_service.queries.mapper.LineProductMapper;
import com.alrussy_dev.order_service.queries.model.LineProduct;
import com.alrussy_dev.order_service.queries.model.dto.LineProductRequest;
import com.alrussy_dev.order_service.queries.model.dto.LineProductResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class LineProductMapperImpl implements LineProductMapper {
    @Override
    public LineProduct toEntity(LineProductRequest request) {
        return LineProduct.builder()
                .curruncy("USD")
                .discount(request.discount())
                .price(request.price())
                .quentity(request.quentity())
                .skuCode(request.skuCode())
                .build();
    }

    @Override
    public LineProductResponse fromEntity(LineProduct entity) {

        var priceAfterDiscount = entity.getPrice() - entity.getPrice() * entity.getDiscount() / 100;
        var totalLine = priceAfterDiscount * entity.getQuentity();
        return new LineProductResponse(
                entity.getSkuCode(),
                entity.getPrice(),
                entity.getDiscount(),
                entity.getCurruncy(),
                priceAfterDiscount,
                entity.getQuentity(),
                totalLine);
    }

    @Override
    public LineProductResponse fromEntityWithSkuProduct(LineProduct entity, SkuProduct skuProduct) {

        return null;
    }
}
