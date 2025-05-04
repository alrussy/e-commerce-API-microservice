package product_app.mapper;

import product_app.model.dto.category_dto.CategoryRequest;
import product_app.model.dto.category_dto.CategoryResponse;
import product_app.model.entities.Category;

public class CategoryMapper {

    public static Category toEntity(CategoryRequest request) {
        var category = Category.builder()
                .name(request.name())
                .isFeature(request.isFeature())
                .imageUrl(request.imageUrl())
                .build();
        return category;
    }

    public static CategoryResponse fromEntity(Category entity) {
        return null;
    }
}
