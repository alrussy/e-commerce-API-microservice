package product_app.model.entities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import product_app.model.dto.category_dto.CategoryResponse;
import product_app.model.entities.table.BrandCategory;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor	
@Entity
@Table(name = "categories")
@EntityListeners(AuditingEntityListener.class)
@Slf4j
public class Category extends Audition {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String imageUrl;
	private Boolean isFeature;

	@OneToMany(mappedBy = "category", targetEntity = BrandCategory.class)
	@Builder.Default
	private List<BrandCategory> brandCategory=new ArrayList<>();


	@OneToMany(mappedBy = "category", targetEntity = Department.class,cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private List<Department> departments=new ArrayList<>();
;
	
	
	public void addBrand(BrandCategory brand) {
		brandCategory.add(brand);
	}
	public void addDepartemnt(Department department) {

		departments.add(department);
	}
	public void addDepartemnt(List<Department> departments) {
				departments.addAll(departments);
	}
	public void removeDepartemnt(Department department) {
		departments.remove(department);
}
	public void removeDepartemnt() {
		departments.clear();}
	public CategoryResponse mapToCategoryResponseWithBrandOutDetailsName() {

		return new CategoryResponse(id, name, isFeature, imageUrl,
				getBrandCategory() != null
						? getBrandCategory().stream().map(bc -> bc.getBrand().mapToBrandResponseOutCategory()).toList()
						: null,
				null);
	}

	public CategoryResponse mapToCategoryResponseOutDetailsNameAndBrand() {

		return new CategoryResponse(id, name, isFeature, imageUrl,null,null);
	}

	public CategoryResponse mapToCategoryResponseWithDetailsNameOutBrand() {
		return new CategoryResponse(id, name, isFeature, imageUrl, null,null);
	}

	public CategoryResponse mapToCategoryResponseWithDetailsNameAndBrandAndDepartment() {
		return new CategoryResponse(id, name, isFeature, imageUrl,
				getBrandCategory() != null
						? getBrandCategory().stream().map(bc -> bc.getBrand().mapToBrandResponseOutCategory()).toList()
						: null,
				departments!=null
				?departments.stream().map(Department::mapToDepartmentResponseOutCategory).toList()
						:null
						
				);
	}
}
