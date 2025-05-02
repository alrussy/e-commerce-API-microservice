package product_app.model.entities;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import product_app.model.dto.department_dto.DepartmentResponse;
import product_app.model.entities.id.DepartmentId;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "departments",uniqueConstraints = @UniqueConstraint(columnNames = {"name","category_id"}))
@EntityListeners(AuditingEntityListener.class)
public class Department extends Audition {

	@EmbeddedId
	private DepartmentId id;
	private String name;
	private Boolean isFeature;
	private String imageUrl;
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	@MapsId("categoryId")
	private Category category;

	


	public DepartmentResponse mapToDepartmentResponseOutCategory() {
		return new DepartmentResponse(id.getDepartmentId(), name, null,isFeature,imageUrl,
				null);
	}

	public DepartmentResponse mapToDepartmentResponseWithCategory() {
		return new DepartmentResponse(id.getDepartmentId(), name,
				category.mapToCategoryResponseOutDetailsNameAndBrand(),isFeature,imageUrl,null);
	}
}
