package product_app.model.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import product_app.model.dto.brand_dto.BrandResponse;
import product_app.model.entities.table.BrandCategory;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "brands")
@EntityListeners(AuditingEntityListener.class)
public class Brand extends Audition {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String imageUrl;
	private Boolean isFeature;

	@OneToMany(mappedBy = "brand", targetEntity = BrandCategory.class, cascade = CascadeType.ALL,orphanRemoval = true)
	@Builder.Default
	private Set<BrandCategory> brandCategory=new HashSet<BrandCategory>();

	public void addCategory(BrandCategory category) {
		brandCategory.add(category);
	}

	public void addAllCategory(List<BrandCategory> categories) {
		brandCategory.addAll(categories);
	}

	public BrandResponse mapToBrandResponseWithCategory() {
		return new BrandResponse(id, name, imageUrl, isFeature,null,


				brandCategory.stream()
						.map(category -> category.getCategory().mapToCategoryResponseOutDetailsNameAndBrand())
						.toList());

	}

	public BrandResponse mapToBrandResponseOutCategory() {
		return new BrandResponse(id, name, imageUrl, isFeature, null, null);
	}
		public BrandResponse mapToBrandResponseWithProductCount(int count) {
		return new BrandResponse(id, name, imageUrl, isFeature,count,null);
	}
		
}