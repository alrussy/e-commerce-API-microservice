package product_app.mapper;

import product_app.model.dto.department_dto.DepartmentRequest;
import product_app.model.dto.department_dto.DepartmentResponse;
import product_app.model.entities.Category;
import product_app.model.entities.Department;
import product_app.model.entities.id.DepartmentId;

public class DepartmentMapper {

    public static Department toEntity(DepartmentRequest request) {

        return Department.builder()
                .name(request.name())
                .isFeature(request.isFeature())
                .imageUrl(request.imageUrl())
                .build();
    }

    public static Department toEntity(Long id, DepartmentRequest request) {
        return Department.builder()
                .id(DepartmentId.builder().categoryId(id).build())
                .category(Category.builder().id(id).build())
                .name(request.name())
                .isFeature(request.isFeature())
                .imageUrl(request.imageUrl())
                .build();
    }

    public static DepartmentResponse fromEntity(Department entity) {
        return null;
    }
}
