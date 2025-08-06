package product_app.mapper;

import product_app.model.dto.department_dto.DepartmentRequest;
import product_app.model.dto.department_dto.DepartmentResponse;
import product_app.model.entities.Department;

public interface DepartmentMapper extends BaseMapper<Department, DepartmentResponse, DepartmentRequest> {

    Department toEntity(Long id, DepartmentRequest request);

    DepartmentResponse fromEntityOutCategory(Department entity);
}
