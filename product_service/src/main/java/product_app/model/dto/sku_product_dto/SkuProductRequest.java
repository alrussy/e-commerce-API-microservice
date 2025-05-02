package product_app.model.dto.sku_product_dto;

import java.util.List;

import product_app.model.entities.Details;
import product_app.model.entities.Product;
import product_app.model.entities.SkuProduct;
import product_app.model.entities.id.ProductId;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SkuProductRequest {

	@NotNull
	private Long productId;
	@NotNull
	private Long categoryId;
	@NotNull
	private Double price;
	@NotNull
	private Double discount;
	@NotNull
	private String currency;
	private Boolean isPrimary;
	@NotNull
	@NotEmpty
	private List<String> imageUrls;
	@NotEmpty
	@NotNull
	private List<Details> details;

	public SkuProduct mapToSkuProduct() {

		
		return SkuProduct.builder().product(Product.builder().id(ProductId.builder().productId(productId).categoryId(categoryId).build()).build()).details(details)
				.imageUrls(imageUrls).price(price).discount(discount).currency(currency).build();
	}
}
