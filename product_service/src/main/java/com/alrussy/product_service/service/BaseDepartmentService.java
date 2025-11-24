package com.alrussy.product_service.service;

import com.alrussy.product_service.model.dto.department_dto.DepartmentRequest;
import com.alrussy.product_service.model.dto.department_dto.DepartmentResponse;
import java.util.List;

public interface BaseDepartmentService extends BaseService<Long, DepartmentResponse, DepartmentRequest> {

    List<DepartmentResponse> saveAll(List<DepartmentRequest> responses);

    List<DepartmentResponse> findByName(String name);

    void deleteAll(List<Long> ids);
}
