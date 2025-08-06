package product_app.mapper.impls;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import product_app.mapper.ProductMapper;
import product_app.model.dto.details_dto.ProductDetailsResponse;
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

@Component
@RequiredArgsConstructor
public class ProductMapperImpl implements ProductMapper {

    private final CategoryMapperImpl categoryMapper;

    @Override
    public Product toEntity(ProductRequest request) {
        ProductId id = ProductId.builder().categoryId(request.categoryId()).build();

        Department department = Department.builder()
                .department(DepartmentId.builder()
                        .id(request.departmentId())
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
        return Product.builder()
                .id(id)
                .name(request.name())
                .brand(Brand.builder().id(request.brandId()).build())
                .department(department)
                .brandCategory(brandCategory)
                .imageUrl(request.imageUrl())
                .about(request.about())
                .build();
    }

    @Override
    public ProductResponse fromEntity(Product entity) {
        return new ProductResponse(
                entity.getId().getProductId(),
                entity.getName(),
                entity.getImageUrl(),
                entity.getDepartment() == null
                        ? null
                        : categoryMapper.fromEntityOutBrandAndDepartments(
                                entity.getDepartment().getCategory()),
                entity.getDepartment() == null
                        ? null
                        : categoryMapper.getDepartmentsMapper().fromEntityOutCategory(entity.getDepartment()),
                categoryMapper.getBrandMapper().fromEntityOutCategory(entity.getBrand()),
                entity.getDetails() == null
                        ? null
                        : entity.getDetails().stream()
                                .map(det -> new ProductDetailsResponse(
                                        det.getId().getName(), det.getId().getValue()))
                                .collect(Collectors.groupingBy(
                                        t -> t.name(), Collectors.mapping(v -> v.value(), Collectors.toList()))),
                entity.getAbout());
    }

    @Override
    public ProductResponse fromEntityWithBrand(Product entity) {
        return new ProductResponse(
                entity.getId().getProductId(),
                entity.getName(),
                entity.getImageUrl(),
                null,
                null,
                categoryMapper.getBrandMapper().fromEntityOutCategory(entity.getBrand()),
                entity.getDetails() == null
                        ? null
                        : entity.getDetails().stream()
                                .map(det -> new ProductDetailsResponse(
                                        det.getId().getName(), det.getId().getValue()))
                                .collect(Collectors.groupingBy(
                                        t -> t.name(), Collectors.mapping(v -> v.value(), Collectors.toSet()))),
                entity.getAbout());
    }

    @Override
    public ProductResponse fromEntityOutCategory(Product entity) {
        return new ProductResponse(
                entity.getId().getProductId(),
                entity.getName(),
                entity.getImageUrl(),
                null,
                entity.getDepartment() == null
                        ? null
                        : categoryMapper.getDepartmentsMapper().fromEntityOutCategory(entity.getDepartment()),
                categoryMapper.getBrandMapper().fromEntityOutCategory(entity.getBrand()),
                entity.getDetails() == null
                        ? null
                        : entity.getDetails().stream()
                                .map(det -> new ProductDetailsResponse(
                                        det.getId().getName(), det.getId().getValue()))
                                .collect(Collectors.groupingBy(
                                        t -> t.name(), Collectors.mapping(v -> v.value(), Collectors.toList()))),
                entity.getAbout());
    }

    @Override
    public ProductResponse fromEntityOutDetails(Product entity) {
        return new ProductResponse(
                entity.getId().getProductId(),
                entity.getName(),
                entity.getImageUrl(),
                null,
                entity.getDepartment() == null
                        ? null
                        : categoryMapper.getDepartmentsMapper().fromEntityOutCategory(entity.getDepartment()),
                categoryMapper.getBrandMapper().fromEntityOutCategory(entity.getBrand()),
                null,
                entity.getAbout());
    }
}
