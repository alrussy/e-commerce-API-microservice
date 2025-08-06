package product_app.mapper.impls;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import product_app.mapper.ProductMapper;
import product_app.mapper.SkuProductMapper;
import product_app.model.dto.details_dto.SkuProductDetailsResponse;
import product_app.model.dto.sku_product_dto.SkuProductRequest;
import product_app.model.dto.sku_product_dto.SkuProductResponse;
import product_app.model.entities.Product;
import product_app.model.entities.SkuProduct;
import product_app.model.entities.id.ProductId;

@Component
@RequiredArgsConstructor
public class SkuProductImpl implements SkuProductMapper {

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
    public SkuProduct toEntity(SkuProductRequest request, boolean isPrimary) {

        return SkuProduct.builder()
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
                entity.getPriceAfterDiscount());
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
                entity.getPriceAfterDiscount());
    }
}
