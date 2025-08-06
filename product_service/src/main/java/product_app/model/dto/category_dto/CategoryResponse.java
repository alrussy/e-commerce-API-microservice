package product_app.model.dto.category_dto;

import java.util.List;
import product_app.model.dto.brand_dto.BrandResponse;
import product_app.model.dto.department_dto.DepartmentResponse;
import product_app.model.dto.details_dto.NameDetailsResponse;

public record CategoryResponse(
        Long id,
        String name,
        String imageUrl,
        List<BrandResponse> brands,
        List<DepartmentResponse> departments,
        List<NameDetailsResponse> Namedetail) {}
