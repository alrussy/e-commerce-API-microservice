package product_app.mapper;

import product_app.model.dto.product_dto.ProductRequest;
import product_app.model.dto.product_dto.ProductResponse;
import product_app.model.entities.Product;

public interface ProductMapper extends BaseMapper<Product, ProductResponse, ProductRequest> {

    ProductResponse fromEntityWithBrand(Product entity);

    ProductResponse fromEntityOutCategory(Product entity);

    ProductResponse fromEntityOutDetails(Product entity);
}
