package product_app.model.dto.department_dto;

import product_app.model.entities.Category;
import product_app.model.entities.Department;
import product_app.model.entities.id.DepartmentId;

public record DepartmentRequest(String name,Boolean isFeature,String imageUrl) {

	public Department mapToDepartment() {
		return Department.builder().name(name).isFeature(isFeature).imageUrl(imageUrl)
				.build();
	}
	public Department mapToDepartment(Long id) {
		return Department.builder().id(DepartmentId.builder().categoryId(id).build()).category(Category.builder().id(id).build()).name(name).isFeature(isFeature).imageUrl(imageUrl)
				.build();
	}
}
