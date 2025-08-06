package product_app.mapper.impls;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import product_app.mapper.BrandMapper;
import product_app.mapper.CategoryMapper;
import product_app.mapper.DepartmentMapper;
import product_app.model.dto.category_dto.CategoryRequest;
import product_app.model.dto.category_dto.CategoryResponse;
import product_app.model.dto.details_dto.NameDetailsResponse;
import product_app.model.entities.Category;

@Component
@Getter
public class CategoryMapperImpl implements CategoryMapper {
    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private DepartmentMapper departmentsMapper;

    @Override
    public Category toEntity(CategoryRequest request) {
        var category = Category.builder()
                .name(request.name())
                .imageUrl(request.imageUrl())
                .build();

        return category;
    }

    @Override
    public CategoryResponse fromEntity(Category entity) {
        return new CategoryResponse(
                entity.getId(),
                entity.getName(),
                entity.getImageUrl(),
                entity.getBrandCategory() == null || entity.getBrandCategory().isEmpty()
                        ? null
                        : entity.getBrandCategory().stream()
                                .map(bc -> brandMapper.fromEntityOutCategory(bc.getBrand()))
                                .toList(),
                entity.getDepartments() != null
                        ? entity.getDepartments().stream()
                                .map(departmentsMapper::fromEntityOutCategory)
                                .toList()
                        : null,
                entity.getNameDetailsCategory().stream()
                        .map(t -> new NameDetailsResponse(t.getId().getDetailNameId()))
                        .toList());
    }

    @Override
    public CategoryResponse fromEntityOutBrandAndDepartments(Category entity) {
        return new CategoryResponse(entity.getId(), entity.getName(), entity.getImageUrl(), null, null, null);
    }
}
