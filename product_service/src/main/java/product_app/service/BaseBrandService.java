package product_app.service;

import java.util.List;
import product_app.model.dto.brand_dto.BrandRequest;
import product_app.model.dto.brand_dto.BrandResponse;

public interface BaseBrandService extends BaseService<Long, BrandResponse, BrandRequest> {

    List<BrandResponse> findByCategory(Long id);

    BrandResponse findByName(String name);

    List<BrandResponse> findAllWithProductCount();

    BrandResponse findByIdWithProductCount(Long id);

    List<BrandResponse> findByCategoryWithProductCount(Long id);
}
