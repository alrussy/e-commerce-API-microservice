package com.alrussy.product_service.mapper.impls;

import com.alrussy.product_service.mapper.ProductMapper;
import com.alrussy.product_service.model.dto.details_dto.ProductDetailsResponse;
import com.alrussy.product_service.model.dto.product_dto.ProductRequest;
import com.alrussy.product_service.model.dto.product_dto.ProductResponse;
import com.alrussy.product_service.model.entities.Brand;
import com.alrussy.product_service.model.entities.Category;
import com.alrussy.product_service.model.entities.Department;
import com.alrussy.product_service.model.entities.Product;
import com.alrussy.product_service.model.entities.Unit;
import com.alrussy.product_service.model.entities.id.BrandCategoryId;
import com.alrussy.product_service.model.entities.id.DepartmentId;
import com.alrussy.product_service.model.entities.id.ProductId;
import com.alrussy.product_service.model.entities.table.BrandCategory;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapperImpl implements ProductMapper {

    private final CategoryMapperImpl categoryMapper;
    private final UnitMapperImpl unitMapper;

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
                .departmentId(request.departmentId())
                .name(request.name())
                .brand(Brand.builder().id(request.brandId()).build())
                .department(department)
                .brandCategory(brandCategory)
                .unit(Unit.builder().id(request.unitId()).build())
                .currency(request.currency())
                .price(request.price())
                .discount(request.discount())
                .imageUrl(request.imageUrl())
                .about(request.about())
                .build();
    }

    @Override
    public ProductResponse fromEntity(Product entity) {
        var category = entity.getDepartment() == null
                ? null
                : categoryMapper.fromEntityOutBrandAndDepartments(
                        entity.getDepartment().getCategory());
        var group = category == null ? null : category.group();
        var details = entity.getDetails() == null
                ? null
                : entity.getDetails().stream()
                        .map(det -> new ProductDetailsResponse(
                                det.getId().getName(), det.getId().getValue()))
                        .collect(Collectors.groupingBy(
                                t -> t.name(), Collectors.mapping(v -> v.value(), Collectors.toList())));
        var department = entity.getDepartment() == null
                ? null
                : categoryMapper.getDepartmentsMapper().fromEntityOutCategory(entity.getDepartment());
        var brand = entity.getBrand() == null
                ? null
                : categoryMapper.getBrandMapper().fromEntityOutCategory(entity.getBrand());

        return new ProductResponse(
                entity.getId().getProductId(),
                entity.getName(),
                entity.getSkuCode(),
                entity.getImageUrl(),
                group,
                category,
                department,
                brand,
                details,
                entity.getAbout(),
                unitMapper.fromEntity(entity.getUnit()),
                entity.getCurrency(),
                entity.getPrice(),
                entity.getDiscount(),
                entity.getPriceAfterDiscount());
    }

    @Override
    public ProductResponse fromEntityWithBrand(Product entity) {

        var details = entity.getDetails() == null
                ? null
                : entity.getDetails().stream()
                        .map(det -> new ProductDetailsResponse(
                                det.getId().getName(), det.getId().getValue()))
                        .collect(Collectors.groupingBy(
                                t -> t.name(), Collectors.mapping(v -> v.value(), Collectors.toList())));
        var brand = entity.getBrand() == null
                ? null
                : categoryMapper.getBrandMapper().fromEntityOutCategory(entity.getBrand());

        return new ProductResponse(
                entity.getId().getProductId(),
                entity.getName(),
                entity.getSkuCode(),
                entity.getImageUrl(),
                null,
                null,
                null,
                brand,
                details,
                entity.getAbout(),
                unitMapper.fromEntity(entity.getUnit()),
                entity.getCurrency(),
                entity.getPrice(),
                entity.getDiscount(),
                entity.getPriceAfterDiscount());
    }

    @Override
    public ProductResponse fromEntityOutCategory(Product entity) {
        var category = entity.getDepartment() == null
                ? null
                : categoryMapper.fromEntityOutBrandAndDepartments(
                        entity.getDepartment().getCategory());
        var group = category == null ? null : category.group();
        var details = entity.getDetails() == null
                ? null
                : entity.getDetails().stream()
                        .map(det -> new ProductDetailsResponse(
                                det.getId().getName(), det.getId().getValue()))
                        .collect(Collectors.groupingBy(
                                t -> t.name(), Collectors.mapping(v -> v.value(), Collectors.toList())));
        var department = entity.getDepartment() == null
                ? null
                : categoryMapper.getDepartmentsMapper().fromEntityOutCategory(entity.getDepartment());
        var brand = entity.getBrand() == null
                ? null
                : categoryMapper.getBrandMapper().fromEntityOutCategory(entity.getBrand());

        return new ProductResponse(
                entity.getId().getProductId(),
                entity.getName(),
                entity.getSkuCode(),
                entity.getImageUrl(),
                group,
                null,
                department,
                brand,
                details,
                entity.getAbout(),
                unitMapper.fromEntity(entity.getUnit()),
                entity.getCurrency(),
                entity.getPrice(),
                entity.getDiscount(),
                entity.getPriceAfterDiscount());
    }

    @Override
    public ProductResponse fromEntityOutDetails(Product entity) {
        var category = entity.getDepartment() == null
                ? null
                : categoryMapper.fromEntityOutBrandAndDepartments(
                        entity.getDepartment().getCategory());
        var group = category == null ? null : category.group();

        var department = entity.getDepartment() == null
                ? null
                : categoryMapper.getDepartmentsMapper().fromEntityOutCategory(entity.getDepartment());
        var brand = entity.getBrand() == null
                ? null
                : categoryMapper.getBrandMapper().fromEntityOutCategory(entity.getBrand());

        return new ProductResponse(
                entity.getId().getProductId(),
                entity.getName(),
                entity.getSkuCode(),
                entity.getImageUrl(),
                group,
                category,
                department,
                brand,
                null,
                entity.getAbout(),
                unitMapper.fromEntity(entity.getUnit()),
                entity.getCurrency(),
                entity.getPrice(),
                entity.getDiscount(),
                entity.getPriceAfterDiscount());
    }
}
