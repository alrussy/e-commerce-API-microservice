package product_app.model.dto.brand_dto;

import java.util.List;

import product_app.model.dto.category_dto.CategoryResponse;
import product_app.model.dto.product_dto.ProductResponse;



public record BrandResponse( Long id, String name, String imageUrl,Boolean isFeature,Integer productCount, List<CategoryResponse> categories){

	
}
