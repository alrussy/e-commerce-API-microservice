package product_app.mapper;

import product_app.model.dto.category_dto.CategoryRequest;
import product_app.model.dto.category_dto.CategoryResponse;
import product_app.model.entities.Category;

public interface CategoryMapper extends BaseMapper<Category, CategoryResponse, CategoryRequest> {

    CategoryResponse fromEntityOutBrandAndDepartments(Category entity);
}
