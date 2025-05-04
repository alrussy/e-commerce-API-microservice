package product_app.service;

import java.util.List;
import product_app.model.dto.department_dto.DepartmentRequest;
import product_app.model.dto.department_dto.DepartmentResponse;

public interface BaseDepartmentService extends BaseService<Long, DepartmentResponse, DepartmentRequest> {

    List<DepartmentResponse> saveAll(List<DepartmentRequest> responses);

    List<DepartmentResponse> findByName(String name);

    void deleteAll(List<Long> ids);
}
