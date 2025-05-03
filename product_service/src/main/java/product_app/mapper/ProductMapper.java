package product_app.mapper;

import org.springframework.data.domain.Page;
import product_app.model.dto.PageResponse;
import product_app.model.dto.product.ProductRequest;
import product_app.model.dto.product.ProductResponse;
import product_app.model.entities.Product;

public class ProductMapper {

    public static Product toEntity(ProductRequest request) {
        return Product.builder().build();
    }

    public static ProductResponse fromEntity(Product entity) {
        return new ProductResponse();
    }

    public static PageResponse<ProductResponse> fromPage(Page<ProductResponse> page) {
        return new PageResponse<ProductResponse>(
                page.getContent(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious());
    }
}
