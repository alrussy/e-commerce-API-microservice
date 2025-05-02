package product_app.model.dto.sku_product_dto;

import java.util.List;

import product_app.model.entities.Details;

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
public class SkuQuentity {

	private String skuCode;
	private Integer quentity;
}
