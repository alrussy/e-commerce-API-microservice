package product_app.service.impls.product_Impls;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import product_app.mapper.PageMapper;
import product_app.mapper.ProductMapper;
import product_app.model.dto.PageResult;
import product_app.model.dto.product_dto.ProductRequest;
import product_app.model.dto.product_dto.ProductResponse;
import product_app.model.entities.Brand;
import product_app.model.entities.Department;
import product_app.model.entities.Product;
import product_app.model.entities.id.DepartmentId;
import product_app.repository.ProductRepository;
import product_app.service.BaseProductService;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceForAdmin implements BaseProductService {

    private final ProductRepository productRepository;

    @Override
    public PageResult<ProductResponse> findAll(int pageNumber) {
        var page = PageRequest.of(pageNumber, 10);

        return new PageMapper<ProductResponse>()
                .toPageResponse(productRepository.findAll(page).map(brand -> ProductMapper.fromEntity(brand)));
    }

    @Override
    public ProductResponse findById(Long id) {
        return productRepository
                .findByIdProductId(id)
                .orElseThrow(() -> new EntityNotFoundException(id.toString()))
                .mapToproductResponseWithCategoryBrandAndDepartment();
    }

    @Override
    public List<ProductResponse> findByBrandId(Long brandId) {
        return productRepository.findByBrandId(brandId).stream()
                .map(Product::mapToproductResponseWithBrand)
                .toList();
    }

    @Override
    public Map<Object, List<ProductResponse>> findByDepartmentIds(List<Long> departmentIds) {
        return productRepository.findByDepartmentIdDepartmentIdIn(departmentIds).stream()
                .map(Product::mapToproductResponseWithCategoryBrandAndDepartment)
                .collect(Collectors.groupingBy(t -> t.department().name()));
    }

    @Override
    public ProductResponse save(ProductRequest productRequest) {

        return productRepository
                .save(ProductMapper.toEntity(productRequest))
                .mapToproductResponseOutCategoryBrandDepartmentAndDetails();
    }

    @Override
    public ProductResponse update(Long id, ProductRequest productRequest) {
        ProductResponse response = null;
        Product product =
                productRepository.findByIdProductId(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
        if (productRequest.name() != null && !productRequest.name().isEmpty()) {
            product.setName(productRequest.name());
        }
        if (productRequest.isFeature() != null) {
            product.setIsFeature(productRequest.isFeature());
        }
        if (productRequest.about() != null) {
            product.setAbout(productRequest.about());
        }

        if (productRequest.imageUrl() != null) {
            product.setImageUrl(productRequest.imageUrl());
        }
        if (productRequest.departmentId() != null && productRequest.categoryId() != null) {
            product.setDepartment(Department.builder()
                    .id(DepartmentId.builder()
                            .departmentId(productRequest.departmentId())
                            .categoryId(productRequest.categoryId())
                            .build())
                    .build());
        }

        if (productRequest.brandId() != null && productRequest.categoryId() != null) {
            log.info(" Brand Id={}", productRequest.brandId());
            product.setBrand(Brand.builder().id(productRequest.brandId()).build());
        }

        response = productRepository.save(product).mapToproductResponseOutCategoryBrandDepartmentAndDetails();
        return response;
    }

    @Override
    public void delete(Long id) {
        Product product =
                productRepository.findByIdProductId(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
        productRepository.deleteAllInBatch(List.of(product));
    }

    public Boolean existsByDepartmentId(Long departmentId) {
        return productRepository.existsByDepartmentIdDepartmentId(departmentId);
    }

    //	@Transactional
    //
    //	public ProductResponse addDetails(Long id, ValueAndNameDetailsId detilsId) {
    //		Product product = productRepository.findByIdProductId(id)
    //				.orElseThrow(() -> new RecordNotFoundException(id.toString()));
    //		var productDetails=ProductDetails.builder()
    //		.id(ProductDetailsId.builder().detailsId(
    //				ValueAndNameDetailsId.builder().value(detilsId.getValue()).name(detilsId.getName()).build())
    //				.productId(product.getId()).build())
    //
    //	.details(ValueDetails.builder().id(detilsId).nameDetails(NameDetails.builder().name(detilsId.getName()).build()).build())
    //		.product(Product.builder().id(product.getId()).build())
    //		.build();
    //
    //
    //		NameDetailsCategory categoryDetailsNames = NameDetailsCategory.builder()
    //
    //	.id(NameDetailsCategoryId.builder().categoryId(product.getId().getCategoryId()).nameDetailsId(detilsId.getName()).build())
    //		.category(Category.builder().id(product.getId().getCategoryId()).build())
    //		.nameDetails(NameDetails.builder().name(detilsId.getName()).build())
    //		.build();
    //
    //		product.addCategoryDetailsName(categoryDetailsNames);
    //		product.addDetails(productDetails);
    //		return productRepository.save(product).mapToproductResponseOutCategoryBrandDepartmentAndDetails();
    //	}

    @Override
    public Long count() {
        // TODO Auto-generated method stub
        return null;
    }
}
