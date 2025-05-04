package product_app.mapper;

import product_app.model.dto.sku_product_dto.SkuProductRequest;
import product_app.model.dto.sku_product_dto.SkuProductResponse;
import product_app.model.entities.Product;
import product_app.model.entities.SkuProduct;
import product_app.model.entities.id.ProductId;

public class SkuProductMapper {

    public static SkuProduct toEntity(SkuProductRequest request) {

        return SkuProduct.builder()
                .product(Product.builder()
                        .id(ProductId.builder()
                                .productId(request.productId())
                                .categoryId(request.categoryId())
                                .build())
                        .build())
                .details(request.details())
                .imageUrls(request.imageUrls())
                .price(request.price())
                .discount(request.discount())
                .currency(request.currency())
                .build();
    }

    public static SkuProduct toEntity(SkuProductRequest request, boolean isPrimary) {

        return SkuProduct.builder()
                .product(Product.builder()
                        .id(ProductId.builder()
                                .productId(request.productId())
                                .categoryId(request.categoryId())
                                .build())
                        .build())
                .isPrimary(isPrimary)
                .details(request.details())
                .imageUrls(request.imageUrls())
                .price(request.price())
                .discount(request.discount())
                .currency(request.currency())
                .build();
    }

    public static SkuProductResponse fromEntity(SkuProduct entity) {
        return null;
    }
}
