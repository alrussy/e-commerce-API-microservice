package product_app.service.impls.product_Impls;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import product_app.mapper.ProductMapper;
import product_app.mapper.impls.PageMapper;
import product_app.model.dto.PagedResult;
import product_app.model.dto.product_dto.ProductRequest;
import product_app.model.dto.product_dto.ProductResponse;
import product_app.model.entities.Product;
import product_app.model.entities.id.ValueDetailsProductId;
import product_app.model.entities.table.ValueDetailsAndProduct;
import product_app.repository.ProductRepository;
import product_app.service.BaseProductService;

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
    public ProductResponse findById(Long id) {
        return productMapper.fromEntity(
                productRepository.findByIdProductId(id).orElseThrow(() -> new EntityNotFoundException(id.toString())));
    }

    @Override
    public List<ProductResponse> findByBrandId(Long brandId) {
        return productRepository.findByBrandId(brandId).stream()
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
        return productRepository.save(product).getId().getProductId();
    }

    @Override
    public ProductResponse update(Long id, ProductRequest productRequest) {

        Product product =
                productRepository.findByIdProductId(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
        if (productRequest.name() != null && !productRequest.name().isEmpty()) {
            product.setName(productRequest.name());
        }
        if (productRequest.about() != null) {
            product.setAbout(productRequest.about());
        }

        if (productRequest.imageUrl() != null) {
            product.setImageUrl(productRequest.imageUrl());
        }

        return productMapper.fromEntityOutCategory(productRepository.save(product));
    }

    @Override
    public void delete(Long id) {
        Product product =
                productRepository.findByIdProductId(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
        productRepository.deleteAllInBatch(List.of(product));
    }

    public Boolean existsByDepartmentId(Long departmentId) {
        return productRepository.existsByDepartmentDepartmentId(departmentId);
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
