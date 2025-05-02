package product_app.model.dto.department_dto;

import java.util.List;

import product_app.model.dto.category_dto.CategoryResponse;
import product_app.model.dto.product_dto.ProductResponse;

public record DepartmentResponse(Long id,String name,CategoryResponse category,Boolean isFeature,String imageUrl,ProductResponse product) {
	
	
	

}
