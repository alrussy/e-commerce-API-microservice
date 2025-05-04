package product_app.service;

import java.util.Collection;
import java.util.List;
import product_app.model.dto.category_dto.CategoryRequest;
import product_app.model.dto.category_dto.CategoryResponse;

public interface BaseCategoryService extends BaseService<Long, CategoryResponse, CategoryRequest> {

    List<CategoryResponse> findByIsFeature();

    Collection<CategoryResponse> findByName(String name);
}
