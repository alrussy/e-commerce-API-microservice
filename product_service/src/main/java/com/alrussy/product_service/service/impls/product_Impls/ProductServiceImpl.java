package com.alrussy.product_service.service.impls.product_Impls;

import jakarta.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alrussy.product_service.mapper.ProductMapper;
import com.alrussy.product_service.mapper.impls.PageMapper;
import com.alrussy.product_service.model.dto.PagedResult;
import com.alrussy.product_service.model.dto.product_dto.ProductFilter;
import com.alrussy.product_service.model.dto.product_dto.ProductRequest;
import com.alrussy.product_service.model.dto.product_dto.ProductResponse;
import com.alrussy.product_service.model.entities.Product;
import com.alrussy.product_service.model.entities.id.ValueDetailsProductId;
import com.alrussy.product_service.model.entities.table.ValueDetailsAndProduct;
import com.alrussy.product_service.repository.ProductRepository;
import com.alrussy.product_service.repository.specification.ProductSpecification;
import com.alrussy.product_service.service.BaseProductService;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements BaseProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public PagedResult<ProductResponse> findAll(int pageNumber) {

        var page = PageRequest.of(pageNumber <= 1 ? 0 : pageNumber - 1, 10);

        return new PageMapper<ProductResponse>()
                .toPageResponse(productRepository.findAll(page).map(product -> productMapper.fromEntity(product)));
    }

    @Override
    public List<ProductResponse> filter(ProductFilter filter) {

        ProductSpecification productSpecification = new ProductSpecification(filter);

        return productRepository.findAll(productSpecification).stream()
                .map(product -> productMapper.fromEntity(product))
                .toList();
    }

    @Override
    public PagedResult<ProductResponse> filter(
            int pageNumber, ProductFilter filter, String direction, String... sortedBy) {

        System.out.println(sortedBy);
        var page = PageRequest.of(
                pageNumber <= 1 ? 0 : pageNumber - 1,
                10,
                Sort.by(Arrays.asList(sortedBy).stream()
                        .map(t -> direction.equalsIgnoreCase("asc")
                                ? Order.asc(getFieldName(t))
                                : Order.desc(getFieldName(t)))
                        .toList()));

        ProductSpecification productSpecification = new ProductSpecification(filter);
        return new PageMapper<ProductResponse>()
                .toPageResponse(productRepository
                        .findAll(productSpecification, page)
                        .map(product -> productMapper.fromEntity(product)));
    }

    @Override
    public ProductResponse findById(Long id) {
        return productMapper.fromEntity(
                productRepository.findByIdProductId(id).orElseThrow(() -> new EntityNotFoundException(id.toString())));
    }

    @Override
    public List<ProductResponse> findByBrandId(Long brandId) {
        return productRepository.findByBrandId(brandId).stream()
                .map(productMapper::fromEntityOutDetails)
                .toList();
    }

    @Override
    public List<ProductResponse> findByBrandIdAndName(Long brandId, String name) {
        return productRepository.findByBrandIdAndNameContaining(brandId, name).stream()
                .map(productMapper::fromEntityOutDetails)
                .toList();
    }

    @Override
    public List<ProductResponse> findByCategoryId(Long categoryId) {
        return productRepository.findByIdCategoryId(categoryId).stream()
                .map(productMapper::fromEntityOutDetails)
                .toList();
    }

    @Override
    public List<ProductResponse> findByCategoryIdAnName(Long categoryId, String name) {
        return productRepository.findByIdCategoryIdAndNameContaining(categoryId, name).stream()
                .map(productMapper::fromEntityWithBrand)
                .toList();
    }

    @Override
    public Map<Object, List<ProductResponse>> findByDepartmentIds(List<String> departmentNames) {
        return productRepository.findByDepartmentNameIn(departmentNames).stream()
                .map(productMapper::fromEntity)
                .collect(Collectors.groupingBy(t -> t.department().name()));
    }

    @Override
    @Transactional
    public Long save(ProductRequest productRequest) {

        Product product = productRepository.save(productMapper.toEntity(productRequest));

        if (productRequest.details() != null) {
            var d = productRequest.details().stream()
                    .flatMap(detail -> detail.values().stream().map(value -> ValueDetailsAndProduct.builder()
                            .id(ValueDetailsProductId.builder()
                                    .productId(product.getId().getProductId())
                                    .categoryId(productRequest.categoryId())
                                    .name(detail.name())
                                    .value(value)
                                    .build())
                            .build()))
                    .collect(Collectors.toSet());
            product.setDetails(d);
        }
        String skuCode = new StringBuilder()
                .append(
                        productRequest.categoryId() <= 9
                                ? "0"
                                        + product.getBrandCategory()
                                                .getCategory()
                                                .getGroupCategory()
                                                .getId()
                                                .toString()
                                : product.getBrandCategory()
                                        .getCategory()
                                        .getGroupCategory()
                                        .getId()
                                        .toString())
                .append(
                        product.getId().getCategoryId() <= 9
                                ? "0"
                                        + product.getId()
                                                .getCategoryId()
                                                .toString()
                                                .toString()
                                : product.getId().getCategoryId().toString())
                .append(
                        product.getBrand().getId() <= 9
                                ? "0" + product.getBrand().getId().toString()
                                : product.getBrand().getId().toString())
                .append(
                        product.getId().getProductId() <= 9
                                ? "0" + product.getId().getProductId().toString()
                                : product.getId().getProductId())
                .toString();
        System.out.println(skuCode);

        product.setSkuCode(skuCode);
        return productRepository.save(product).getId().getProductId();
    }

    @Override
    @Transactional
    public ProductResponse update(Long id, ProductRequest productRequest) {

        Product product =
                productRepository.findByIdProductId(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
        if (productRequest.name() != null && !productRequest.name().isEmpty()) {
            product.setName(productRequest.name());
        }
        if (productRequest.price() != null) {
            product.setPrice(productRequest.price());
        }
        if (productRequest.discount() != null) {
            product.setDiscount(productRequest.discount());
        }
        if (productRequest.about() != null) {
            product.setAbout(productRequest.about());
        }

        if (productRequest.imageUrl() != null) {
            product.setImageUrl(productRequest.imageUrl());
        }
        if (productRequest.details() != null) {

            productRepository.deleteDetails(id);
            var d = productRequest.details().stream()
                    .flatMap(detail -> detail.values().stream().map(value -> ValueDetailsAndProduct.builder()
                            .id(ValueDetailsProductId.builder()
                                    .productId(product.getId().getProductId())
                                    .categoryId(product.getId().getCategoryId())
                                    .name(detail.name())
                                    .value(value)
                                    .build())
                            .build()))
                    .collect(Collectors.toSet());
            product.setDetails(d);
        }
        return productMapper.fromEntityOutCategory(productRepository.save(product));
    }

    @Override
    public void delete(Long id) {
        Product product =
                productRepository.findByIdProductId(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
        productRepository.delete(product);
    }

    public Boolean existsByDepartmentId(Long departmentId) {
        return productRepository.existsByDepartmentDepartmentId(departmentId);
    }

    // @Transactional
    //
    // public ProductResponse addDetails(Long id, ValueAndNameDetailsId detilsId) {
    // Product product = productRepository.findByIdProductId(id)
    // .orElseThrow(() -> new RecordNotFoundException(id.toString()));
    // var productDetails=ProductDetails.builder()
    // .id(ProductDetailsId.builder().detailsId(
    // ValueAndNameDetailsId.builder().value(detilsId.getValue()).name(detilsId.getName()).build())
    // .productId(product.getId()).build())
    //
    // .details(ValueDetails.builder().id(detilsId).nameDetails(NameDetails.builder().name(detilsId.getName()).build()).build())
    // .product(Product.builder().id(product.getId()).build())
    // .build();
    //
    //
    // NameDetailsCategory categoryDetailsNames = NameDetailsCategory.builder()
    //
    // .id(NameDetailsCategoryId.builder().categoryId(product.getId().getCategoryId()).nameDetailsId(detilsId.getName()).build())
    // .category(Category.builder().id(product.getId().getCategoryId()).build())
    // .nameDetails(NameDetails.builder().name(detilsId.getName()).build())
    // .build();
    //
    // product.addCategoryDetailsName(categoryDetailsNames);
    // product.addDetails(productDetails);
    // return
    // productRepository.save(product).mapToproductResponseOutCategoryBrandDepartmentAndDetails();
    // }

    @Override
    public Long count() {
        return null;
    }

    @Override
    public List<ProductResponse> findByName(String name) {
        // TODO Auto-generated method stub
        return productRepository.findByNameContaining(name).stream()
                .map(product -> productMapper.fromEntity(product))
                .toList();
    }

    @Override
    public List<ProductResponse> findBySkuCode(String skuCode) {
        // TODO Auto-generated method stub
        return productRepository.findBySkuCodeStartsWith(skuCode).stream()
                .map(product -> productMapper.fromEntity(product))
                .toList();
    }

    private String getFieldName(String content) {
        if (content.toLowerCase().startsWith("category")) {
            return "departmentCategoryName";
        } else if (content.toLowerCase().startsWith("group")) {
            return "departmentCategoryGroupCategoryName";
        } else if (content.toLowerCase().startsWith("brand")) {
            return "brandName";
        } else return content;
    }
}
