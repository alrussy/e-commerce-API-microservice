package com.alrussy.product_service.mapper.impls;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.alrussy.product_service.mapper.ProductMapper;
import com.alrussy.product_service.mapper.SkuProductMapper;
import com.alrussy.product_service.model.dto.details_dto.SkuProductDetailsResponse;
import com.alrussy.product_service.model.dto.sku_product_dto.SkuProductRequest;
import com.alrussy.product_service.model.dto.sku_product_dto.SkuProductResponse;
import com.alrussy.product_service.model.entities.Product;
import com.alrussy.product_service.model.entities.SkuProduct;
import com.alrussy.product_service.model.entities.id.ProductId;

@Component
@RequiredArgsConstructor
public class SkuProductMapperImpl implements SkuProductMapper {

    private final ProductMapper productMapper;

    @Override
    public SkuProduct toEntity(SkuProductRequest request) {

        return SkuProduct.builder()
                .product(Product.builder()
                        .id(ProductId.builder()
                                .productId(request.productId())
                                .categoryId(request.categoryId())
                                .build())
                        .build())
                .imageUrls(request.imageUrls())
                .price(request.price())
                .discount(request.discount())
                .currency(request.currency())
                .build();
    }

    @Override
    public SkuProduct toEntity(SkuProductRequest request, String skuCode, boolean isPrimary) {

        return SkuProduct.builder()
                .skuCode(skuCode)
                .product(Product.builder()
                        .id(ProductId.builder()
                                .productId(request.productId())
                                .categoryId(request.categoryId())
                                .build())
                        .build())
                .isPrimary(isPrimary)
                .imageUrls(request.imageUrls())
                .price(request.price())
                .discount(request.discount())
                .currency(request.currency())
                .build();
    }

    @Override
    public SkuProductResponse fromEntity(SkuProduct entity) {
        return new SkuProductResponse(
                entity.getSkuCode(),
                productMapper.fromEntity(entity.getProduct()),
                entity.getDetails() == null
                        ? null
                        : entity.getDetails().stream()
                                .map(det -> new SkuProductDetailsResponse(
                                        det.getValueDetailsAndProduct().getId().getName(),
                                        det.getValueDetailsAndProduct().getId().getValue()))
                                .collect(Collectors.toMap(
                                        SkuProductDetailsResponse::name, SkuProductDetailsResponse::value)),
                entity.getImageUrls(),
                entity.getPrice(),
                entity.getDiscount(),
                entity.getCurrency(),
                entity.getPriceAfterDiscount(),
                null,
                null,
                null,
                null,
                null);
    }

    @Override
    public SkuProductResponse fromEntityWithActionSummury(
            SkuProduct entity, Integer openingStock, Integer incoming, Integer outoing, Integer stock) {
        return new SkuProductResponse(
                entity.getSkuCode(),
                productMapper.fromEntityOutDetails(entity.getProduct()),
                entity.getDetails() == null
                        ? null
                        : entity.getDetails().stream()
                                .map(det -> new SkuProductDetailsResponse(
                                        det.getValueDetailsAndProduct().getId().getName(),
                                        det.getValueDetailsAndProduct().getId().getValue()))
                                .collect(Collectors.toMap(
                                        SkuProductDetailsResponse::name, SkuProductDetailsResponse::value)),
                entity.getImageUrls(),
                entity.getPrice(),
                entity.getDiscount(),
                entity.getCurrency(),
                entity.getPriceAfterDiscount(),
                entity.getProduct().getUnit().getName(),
                stock,
                incoming,
                outoing,
                openingStock);
    }

    @Override
    public SkuProductResponse fromEntityWithStock(SkuProduct entity, Integer stock) {
        return new SkuProductResponse(
                entity.getSkuCode(),
                productMapper.fromEntityOutDetails(entity.getProduct()),
                entity.getDetails() == null
                        ? null
                        : entity.getDetails().stream()
                                .map(det -> new SkuProductDetailsResponse(
                                        det.getValueDetailsAndProduct().getId().getName(),
                                        det.getValueDetailsAndProduct().getId().getValue()))
                                .collect(Collectors.toMap(
                                        SkuProductDetailsResponse::name, SkuProductDetailsResponse::value)),
                entity.getImageUrls(),
                entity.getPrice(),
                entity.getDiscount(),
                entity.getCurrency(),
                entity.getPriceAfterDiscount(),
                entity.getProduct().getUnit().getName(),
                stock,
                null,
                null,
                null);
    }

    @Override
    public SkuProductResponse fromEntityOutPrduct(SkuProduct entity, Integer stock) {
        return new SkuProductResponse(
                entity.getSkuCode(),
                null,
                entity.getDetails() == null
                        ? null
                        : entity.getDetails().stream()
                                .map(det -> new SkuProductDetailsResponse(
                                        det.getValueDetailsAndProduct().getId().getName(),
                                        det.getValueDetailsAndProduct().getId().getValue()))
                                .collect(Collectors.toMap(
                                        SkuProductDetailsResponse::name, SkuProductDetailsResponse::value)),
                entity.getImageUrls(),
                entity.getPrice(),
                entity.getDiscount(),
                entity.getCurrency(),
                entity.getPriceAfterDiscount(),
                entity.getProduct().getUnit().getName(),
                stock,
                null,
                null,
                null);
    }

    @Override
    public SkuProductResponse fromEntityOutPrduct(SkuProduct entity) {
        return new SkuProductResponse(
                entity.getSkuCode(),
                null,
                entity.getDetails() == null
                        ? null
                        : entity.getDetails().stream()
                                .map(det -> new SkuProductDetailsResponse(
                                        det.getValueDetailsAndProduct().getId().getName(),
                                        det.getValueDetailsAndProduct().getId().getValue()))
                                .collect(Collectors.toMap(
                                        SkuProductDetailsResponse::name, SkuProductDetailsResponse::value)),
                entity.getImageUrls(),
                entity.getPrice(),
                entity.getDiscount(),
                entity.getCurrency(),
                entity.getPriceAfterDiscount(),
                null,
                null,
                null,
                null,
                null);
    }
}
