package product_app.model.dto.brand_dto;

import java.util.List;

import product_app.model.entities.Brand;
import product_app.model.entities.Category;
import product_app.model.entities.id.BrandCategoryId;
import product_app.model.entities.table.BrandCategory;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class BrandRequest {

	private String name;
	private String imageUrl;
	private List<Long> categoryIds;
	private Boolean isFeature;

	public Brand mapToBrand() {
		return Brand.builder().name(name).imageUrl(imageUrl).isFeature(isFeature)
	.build();

	}
}
