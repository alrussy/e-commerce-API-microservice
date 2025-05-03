package product_app.model.dto.sku_product_dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import product_app.model.dto.product_dto.ProductResponse;
import product_app.model.entities.Details;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SkuProductResponse {

    private String skuCode;
    private ProductResponse product;
    private List<Details> details;
    private List<String> imageUrls;
    private Double price;
    private Double discount;
    private String currency;
    private Double priceAfterDiscount;
    private Integer quntity;
}
