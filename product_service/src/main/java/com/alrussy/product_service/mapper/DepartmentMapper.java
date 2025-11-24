package com.alrussy.product_service.mapper;

import com.alrussy.product_service.model.dto.department_dto.DepartmentRequest;
import com.alrussy.product_service.model.dto.department_dto.DepartmentResponse;
import com.alrussy.product_service.model.entities.Department;

public interface DepartmentMapper extends BaseMapper<Department, DepartmentResponse, DepartmentRequest> {

    Department toEntity(Long id, DepartmentRequest request);

    DepartmentResponse fromEntityOutCategory(Department entity);
}
