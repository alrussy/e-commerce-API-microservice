package product_app.model.dto.product_dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import product_app.model.entities.Brand;
import product_app.model.entities.Category;
import product_app.model.entities.Department;
import product_app.model.entities.Product;
import product_app.model.entities.id.BrandCategoryId;
import product_app.model.entities.id.DepartmentId;
import product_app.model.entities.id.ProductId;
import product_app.model.entities.table.BrandCategory;

public record ProductRequest(
        @NotBlank(message = "name is blank it must be content one letter  ") String name,
        Boolean isFeature,
        @NotNull Long categoryId,
        @NotNull Long departmentId,
        @NotNull Long brandId,
        @NotBlank String imageUrl,
        String about) {

    public Product mapToProduct() {
        Department department = Department.builder()
                .id(DepartmentId.builder()
                        .departmentId(departmentId)
                        .categoryId(categoryId)
                        .build())
                .build();
        BrandCategory brandCategory = BrandCategory.builder()
                .brandCategoryId(BrandCategoryId.builder()
                        .brandId(brandId)
                        .categoryId(categoryId)
                        .build())
                .category(Category.builder().id(categoryId).build())
                .build();
        ProductId id = ProductId.builder().categoryId(categoryId).build();
        return Product.builder()
                .id(id)
                .name(name)
                .isFeature(isFeature == null ? false : isFeature)
                .brand(Brand.builder().id(brandId).build())
                .department(department)
                .brandCategory(brandCategory)
                .imageUrl(imageUrl)
                .about(about)
                .build();
    }
}
