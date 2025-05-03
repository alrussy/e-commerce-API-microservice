package product_app.model.dto.category_dto;

import java.util.List;
import product_app.model.dto.brand_dto.BrandResponse;
import product_app.model.dto.department_dto.DepartmentResponse;

// public record CategoryResponse(Long id, String name, Boolean isFeature, String imageUrl,List<BrandResponse>
// brands,List<DetailsNameResponse> detailsName,List<DepartmentResponse> departments) {
public record CategoryResponse(
        Long id,
        String name,
        Boolean isFeature,
        String imageUrl,
        List<BrandResponse> brands,
        List<DepartmentResponse> departments) {}
