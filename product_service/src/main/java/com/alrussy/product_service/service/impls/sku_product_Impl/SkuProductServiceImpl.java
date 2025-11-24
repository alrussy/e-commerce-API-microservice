package com.alrussy.product_service.service.impls.sku_product_Impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alrussy.product_service.mapper.SkuProductMapper;
import com.alrussy.product_service.mapper.impls.PageMapper;
import com.alrussy.product_service.model.dto.PagedResult;
import com.alrussy.product_service.model.dto.Event.OpeningStockEvent;
import com.alrussy.product_service.model.dto.sku_product_dto.SkuProductFilter;
import com.alrussy.product_service.model.dto.sku_product_dto.SkuProductRequest;
import com.alrussy.product_service.model.dto.sku_product_dto.SkuProductResponse;
import com.alrussy.product_service.model.entities.SkuProduct;
import com.alrussy.product_service.model.entities.id.ValueDetailsProductId;
import com.alrussy.product_service.model.entities.table.DetailsOfSku;
import com.alrussy.product_service.model.entities.table.ValueDetailsAndProduct;
import com.alrussy.product_service.publish.OpeningStockEventPublisher;
import com.alrussy.product_service.repository.SkuProductRepository;
import com.alrussy.product_service.repository.specification.SkuProductSpecification;
import com.alrussy.product_service.service.BaseSkuProductService;

@Service
@RequiredArgsConstructor
@Slf4j
public class SkuProductServiceImpl implements BaseSkuProductService {

    private final SkuProductRepository skuProductRepository;
    private final SkuProductMapper skuProductMapper;
    private final OpeningStockEventPublisher publisher;

    @Override
    public PagedResult<SkuProductResponse> findAll(int pageNumber) {

        return new PageMapper<SkuProductResponse>()
                .toPageResponse(findAllEntity(pageNumber).map(sku -> skuProductMapper.fromEntity(sku)));
    }

    public Page<SkuProduct> findAllEntity(int pageNumber) {

        var page = PageRequest.of(pageNumber <= 1 ? 0 : pageNumber - 1, 10);

        return skuProductRepository.findAll(page);
    }

    public List<?> getAllDetailsByProductId(Long productId) {
        List<SkuProductResponse> skuProducts = findByProduct(productId);

        return skuProducts.stream().map(t -> t.details()).toList();
    }

    @Override
    public PagedResult<SkuProductResponse> findByIsPrimary(Integer pageNumber, Integer pageSize) {
        var page = PageRequest.of(pageNumber <= 1 ? 0 : pageNumber - 1, 10);

        return new PageMapper<SkuProductResponse>()
                .toPageResponse(
                        skuProductRepository.findAllByIsPrimary(page, true).map(skuProductMapper::fromEntity));
    }

    @Override
    public PagedResult<SkuProductResponse> findByProductSkuCodesInAndIsPrimary(
            List<String> skuCodes, Integer pageNumber, Integer pageSize) {
        var page = PageRequest.of(pageNumber <= 1 ? 0 : pageNumber - 1, 10);

        return new PageMapper<SkuProductResponse>()
                .toPageResponse(skuProductRepository
                        .findAllByIsPrimaryAndProductSkuCodeIn(page, true, skuCodes)
                        .map(skuProductMapper::fromEntity));
    }

    @Override
    public PagedResult<SkuProductResponse> filter(
            int pageNumber, SkuProductFilter filter, String direction, String... sortedBy) {

        System.out.println(sortedBy);
        var page = PageRequest.of(
                pageNumber <= 1 ? 0 : pageNumber - 1,
                10,
                Sort.by(Arrays.asList(sortedBy).stream()
                        .map(t -> direction.equalsIgnoreCase("asc")
                                ? Order.asc(getFieldName(t))
                                : Order.desc(getFieldName(t)))
                        .toList()));

        SkuProductSpecification skuProductSpecification = new SkuProductSpecification(filter);
        return new PageMapper<SkuProductResponse>()
                .toPageResponse(skuProductRepository
                        .findAll(skuProductSpecification, page)
                        .map(skuProduct -> skuProductMapper.fromEntity(skuProduct)));
    }

    @Override
    public List<SkuProductResponse> findByValueDetails(Long productId, List<String> values) {
        List<SkuProductResponse> skuProducts = findByProduct(productId);
        int i = 0;
        List<SkuProductResponse> filter = new ArrayList<>();
        while (i < skuProducts.size()) {
            if (skuProducts.get(i).details().values().containsAll(values)) {
                filter.add(skuProducts.get(i));
            }
            i++;
        }
        log.info(filter.toString());
        return filter;
    }

    @Override
    public SkuProductResponse findById(String skuCode) {
        return skuProductMapper.fromEntity(
                skuProductRepository.findBySkuCode(skuCode).orElseThrow(() -> new EntityNotFoundException(skuCode)));
    }

    @Override
    public List<SkuProductResponse> findByIds(List<String> skuCodes) {
        return skuProductRepository.findBySkuCodeIn(skuCodes).stream()
                .map(skuProductMapper::fromEntity)
                .toList();
    }

    @Override
    public List<SkuProductResponse> findByProduct(Long productId) {
        return skuProductRepository.findByProductIdProductId(productId).stream()
                .map(skuProductMapper::fromEntity)
                .toList();
    }

    @Override
    public List<SkuProductResponse> findByCategoryId(Long categoryId) {
        return skuProductRepository.findByProductIdCategoryIdAndIsPrimaryTrue(categoryId).stream()
                .map(skuProductMapper::fromEntity)
                .toList();
    }

    @Override
    public Map<Object, List<SkuProductResponse>> findByDepartmentIds(List<Long> departmentIds) {
        List<SkuProductResponse> sResponse = new ArrayList<>();
        departmentIds.stream().forEach(t -> {
            var skuProducts =
                    skuProductRepository.findFirst5ByProductDepartmentDepartmentIdAndIsPrimary(t, true).stream()
                            .map(skuProductMapper::fromEntity)
                            .toList();
            sResponse.addAll(skuProducts);
        });
        return sResponse.stream()
                .collect(Collectors.groupingBy(t -> t.product().department().name()));
    }

    @Override
    public List<SkuProductResponse> findByDepartmentId(Long departmentId) {

        return skuProductRepository.findFirst5ByProductDepartmentDepartmentIdAndIsPrimary(departmentId, true).stream()
                .map(skuProductMapper::fromEntity)
                .toList();
    }

    @Override
    public Map<Object, List<SkuProductResponse>> findByBrandIds(List<Long> brandIds) {
        List<SkuProductResponse> sResponse = new ArrayList<>();
        brandIds.stream().forEach(t -> {
            var skuProducts = skuProductRepository.findFirst3ByProductBrandIdAndIsPrimary(t, true).stream()
                    .map(skuProductMapper::fromEntity)
                    .toList();
            sResponse.addAll(skuProducts);
        });
        return sResponse.stream()
                .collect(Collectors.groupingBy(t -> t.product().brand().name()));
    }

    @Override
    public List<SkuProductResponse> findByBrandId(Long brandId) {

        return skuProductRepository.findFirst3ByProductBrandIdAndIsPrimary(brandId, true).stream()
                .map(skuProductMapper::fromEntity)
                .toList();
    }

    @Override
    @Transactional
    public String save(SkuProductRequest skuProductRequest) {
        String skuCode = "SKU_";
        System.out.println(skuProductRequest.skuCodePublic());
        List<SkuProduct> skus = skuProductRepository.findByProductIdProductId(skuProductRequest.productId());
        if (skus.size() <= 0) {
            skuCode = skuCode + skuProductRequest.skuCodePublic() + "_1";
        } else {
            List<SkuProduct> afterOrder = skus.stream()
                    .sorted(Collections.reverseOrder(Comparator.comparing(SkuProduct::getSkuCode)))
                    .toList();

            String end = afterOrder
                    .getFirst()
                    .getSkuCode()
                    .substring((skuCode + skuProductRequest.skuCodePublic()).length() + 1);
            skuCode = skuCode + skuProductRequest.skuCodePublic() + "_" + (Integer.parseInt(end) + 1);
        }

        var sku = skuProductRepository.save(skuProductMapper.toEntity(skuProductRequest, skuCode, skus.size() <= 0));

        var skuDetails = skuProductRequest.details().stream()
                .map(details -> DetailsOfSku.builder()
                        .valueDetailsAndProduct(ValueDetailsAndProduct.builder()
                                .id(ValueDetailsProductId.builder()
                                        .name(details.name())
                                        .value(details.value())
                                        .productId(skuProductRequest.productId())
                                        .categoryId(skuProductRequest.categoryId())
                                        .build())
                                .build())
                        .skuProduct(sku)
                        .build())
                .collect(Collectors.toSet());

        sku.setDetails(skuDetails);

        publisher.publish("opening-stock50", new OpeningStockEvent(skuCode, skuProductRequest.openingStock()));
        return "sku" + skuProductRepository.save(sku).getSkuCode();
    }

    @Override
    public SkuProductResponse update(String skuCode, SkuProductRequest skuRequest) {

        SkuProduct skuFind =
                skuProductRepository.findBySkuCode(skuCode).orElseThrow(() -> new EntityNotFoundException(skuCode));
        log.info(skuCode);
        if (skuRequest.price() != null) {
            skuFind.setPrice(skuRequest.price());
        }
        if (skuRequest.discount() != null) {
            skuFind.setDiscount(skuRequest.discount());
        }

        if (skuRequest.currency() != null && !skuRequest.currency().isEmpty()) {
            skuFind.setCurrency(skuRequest.currency());
        }

        if (skuRequest.imageUrls() != null || !skuRequest.imageUrls().isEmpty()) {
            // List<String> temp =
            // skuFind.getImageUrls().stream().collect(Collectors.toList());
            // skuRequest.getImageUrls().stream().forEach(t -> temp.add(t));
            skuFind.getImageUrls().clear();
            skuFind.setImageUrls(skuRequest.imageUrls());
        }
        if (skuRequest.details() != null || !skuRequest.details().isEmpty()) {
            // List<Details> temp =
            // skuFind.getDetails().stream().collect(Collectors.toList());
            skuFind.getDetails().clear();
            // skuFind.setDetails(skuRequest.details());
        }

        if (skuRequest.openingStock() != null) {
            publisher.publish("opening-stock50", new OpeningStockEvent(skuCode, skuRequest.openingStock()));
        }
        return skuProductMapper.fromEntity(skuProductRepository.save(skuFind));
    }

    @Override
    public void delete(String skuCode) {
        skuProductRepository.deleteById(skuCode);
    }

    public SkuProductResponse addAndRemoveImage(String skuCode, String url, int typeAction) {
        SkuProduct skuproduct =
                skuProductRepository.findById(skuCode).orElseThrow(() -> new EntityNotFoundException(skuCode));
        switch (typeAction) {
            case 0: {
                skuproduct.removeImage(url);
                return skuProductMapper.fromEntity(skuProductRepository.save(skuproduct));
            }

            case 1: {
                skuproduct.addImage(url);
                return skuProductMapper.fromEntity(skuProductRepository.save(skuproduct));
            }
            default:
                throw new IllegalArgumentException("typeAction mustbe not equale :" + typeAction
                        + " make to variable typeaction =0 for remove image or  typeaction =1 for add image  ");
        }
    }

    // private List<SkuProductResponse> getQuentity(List<SkuProductResponse>
    // skuProducts) {
    // var skuQuentities= skuProducts.stream().map(t->
    // SkuQuentity.builder().skuCode(t.getSkuCode()).quentity(new
    // Random().nextInt(10, 50)).build()).toList();
    // System.out.println(skuQuentities);
    // return skuProducts.stream().map(sku->{
    // skuQuentities.stream().forEach(t->{
    // if(t.getSkuCode().equals(sku.getSkuCode())) {
    // sku.setQuntity(t.getQuentity());
    // }});
    // return sku;
    // }).toList();
    // }

    // private SkuProductResponse getQuentity(SkuProductResponse skuProduct) {
    // //client.get().uri("/api/inventory/add-sku").bodyValue(skuCode).retrieve().bodyToMono(Integer.class).block();
    //
    // var skuQuentity=
    // SkuQuentity.builder().skuCode(skuProduct.getSkuCode()).quentity(new
    // Random().nextInt(10,
    // 50)).build();
    // System.out.println(skuQuentity);
    // skuProduct.setQuntity(skuQuentity.getQuentity());
    // return skuProduct;
    // }

    @Override
    public Long count() {
        // TODO Auto-generated method stub
        return null;
    }

    private String getFieldName(String content) {
        if (content.toLowerCase().startsWith("product")) {
            return "productName";
        } else return content;
    }
}
