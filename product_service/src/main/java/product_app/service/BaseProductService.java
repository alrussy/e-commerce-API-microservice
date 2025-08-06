package product_app.service;

import java.util.List;
import java.util.Map;
import product_app.model.dto.product_dto.ProductRequest;
import product_app.model.dto.product_dto.ProductResponse;

public interface BaseProductService extends BaseService<Long, ProductResponse, ProductRequest> {

    List<ProductResponse> findByBrandId(Long bransId);

    Map<Object, List<ProductResponse>> findByDepartmentIds(List<String> departmentIds);
}
