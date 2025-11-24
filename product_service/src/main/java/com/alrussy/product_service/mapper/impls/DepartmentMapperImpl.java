package com.alrussy.product_service.mapper.impls;

import com.alrussy.product_service.mapper.DepartmentMapper;
import com.alrussy.product_service.model.dto.department_dto.DepartmentRequest;
import com.alrussy.product_service.model.dto.department_dto.DepartmentResponse;
import com.alrussy.product_service.model.entities.Category;
import com.alrussy.product_service.model.entities.Department;
import com.alrussy.product_service.model.entities.id.DepartmentId;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapperImpl implements DepartmentMapper {

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
