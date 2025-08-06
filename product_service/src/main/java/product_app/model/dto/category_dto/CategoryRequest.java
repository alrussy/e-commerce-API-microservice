package product_app.model.dto.category_dto;

import java.util.List;
import product_app.model.dto.department_dto.DepartmentRequest;

public record CategoryRequest(
        String name, String imageUrl, List<DepartmentRequest> departments, List<String> namesDetails) {}
