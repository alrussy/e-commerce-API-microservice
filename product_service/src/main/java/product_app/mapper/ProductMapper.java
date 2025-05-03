package product_app.mapper;

import product_app.model.dto.product_dto.ProductRequest;
import product_app.model.dto.product_dto.ProductResponse;
import product_app.model.entities.Brand;
import product_app.model.entities.Category;
import product_app.model.entities.Department;
import product_app.model.entities.Product;
import product_app.model.entities.id.BrandCategoryId;
import product_app.model.entities.id.DepartmentId;
import product_app.model.entities.id.ProductId;
import product_app.model.entities.table.BrandCategory;

public class ProductMapper {

    public static Product toEntity(ProductRequest request) {
        Department department = Department.builder()
                .id(DepartmentId.builder()
                        .departmentId(request.departmentId())
                        .categoryId(request.categoryId())
                        .build())
                .build();
        BrandCategory brandCategory = BrandCategory.builder()
                .brandCategoryId(BrandCategoryId.builder()
                        .brandId(request.brandId())
                        .categoryId(request.categoryId())
                        .build())
                .category(Category.builder().id(request.categoryId()).build())
                .build();
        ProductId id = ProductId.builder().categoryId(request.categoryId()).build();
        return Product.builder()
                .id(id)
                .name(request.name())
                .isFeature(request.isFeature() == null ? false : request.isFeature())
                .brand(Brand.builder().id(request.brandId()).build())
                .department(department)
                .brandCategory(brandCategory)
                .imageUrl(request.imageUrl())
                .about(request.about())
                .build();
    }

    public static ProductResponse fromEntity(Product entity) {
        return new ProductResponse(
                entity.getId().getProductId(),
                entity.getName(),
                entity.getIsFeature(),
                entity.getImageUrl(),
                entity.getDepartment() == null
                        ? null
                        : entity.getDepartment().getCategory().mapToCategoryResponseWithDetailsNameOutBrand(),
                entity.getDepartment() == null ? null : DepartmentMapper.fromEntity(entity.getDepartment()),
                BrandMapper.fromEntity(entity.getBrand()),
                entity.getAbout());
    }
}
