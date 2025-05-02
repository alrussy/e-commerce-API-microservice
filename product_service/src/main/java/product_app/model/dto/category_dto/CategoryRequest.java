package product_app.model.dto.category_dto;

import java.util.List;

import product_app.model.dto.department_dto.DepartmentRequest;
import product_app.model.entities.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {

	private String name;
	private Boolean isFeature;
	private String imageUrl;
	private List<Long> brandIds;
	private List<DepartmentRequest> departments;

	public Category mapToCategory() {
	
		var category= Category.builder()
		.name(name).isFeature(isFeature).imageUrl(imageUrl)
			
				.build();
		return  category;
		
	}

	
}
