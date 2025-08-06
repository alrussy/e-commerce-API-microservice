package product_app.mapper.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import product_app.mapper.CategoryMapper;
import product_app.mapper.DepartmentMapper;
import product_app.model.dto.department_dto.DepartmentRequest;
import product_app.model.dto.department_dto.DepartmentResponse;
import product_app.model.entities.Category;
import product_app.model.entities.Department;
import product_app.model.entities.id.DepartmentId;

@Component
public class DepartmentMapperImpl implements DepartmentMapper {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Department toEntity(DepartmentRequest request) {
        return Department.builder().name(request.name()).build();
    }

    @Override
    public Department toEntity(Long categoryId, DepartmentRequest request) {
        return Department.builder()
                .department(DepartmentId.builder().categoryId(categoryId).build())
                .name(request.name())
                .category(Category.builder().id(categoryId).build())
                .build();
    }

    @Override
    public DepartmentResponse fromEntity(Department entity) {
        return new DepartmentResponse(entity.getDepartment().getId(), entity.getName());
    }

    @Override
    public DepartmentResponse fromEntityOutCategory(Department entity) {
        return new DepartmentResponse(entity.getDepartment().getId(), entity.getName());
    }
}
