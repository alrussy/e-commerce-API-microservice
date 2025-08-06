package product_app.service;

import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import product_app.model.dto.sku_product_dto.SkuProductRequest;
import product_app.model.dto.sku_product_dto.SkuProductResponse;

public interface BaseSkuProductService extends BaseService<String, SkuProductResponse, SkuProductRequest> {

    Page<SkuProductResponse> findByIsPrimary(Integer pageNumber, Integer pageSize);

    List<SkuProductResponse> findByValueDetails(Long productId, List<String> values);

    List<SkuProductResponse> findByIds(List<String> skuCodes);

    List<SkuProductResponse> findByProduct(Long productId);

    List<SkuProductResponse> findByCategoryId(Long categoryId);

    Map<Object, List<SkuProductResponse>> findByBrandIds(List<Long> brandIds);

    List<SkuProductResponse> findByBrandId(Long brandId);

    Map<Object, List<SkuProductResponse>> findByDepartmentIds(List<Long> departmentIds);

    List<SkuProductResponse> findByDepartmentId(Long departmentId);
}
