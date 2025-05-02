package product_app.model.entities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import product_app.model.dto.sku_product_dto.SkuProductResponse;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

@Entity
@Table(name = "sku-products")

@EntityListeners(AuditingEntityListener.class)
public class SkuProduct extends Audition {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String skuCode;
	@ManyToOne
	private Product product;	
	private Double price;
	private Double discount;
	private String currency;
	private Boolean isPrimary;
	
	@ElementCollection
	@Builder.Default
	private List<String> imageUrls=new ArrayList();
	@Transient
	private Double priceAfterDiscount;
	
	@OneToMany(cascade = CascadeType.ALL)
	@Builder.Default
	private List<Details> details = new ArrayList();
	
	public void addImage(String filename) {
		imageUrls.add(filename);
	}
	public void removeImage(String filename) {
		imageUrls.remove(filename);
		
	}
	public void addDetail(Details detail) {
		details.add(detail);
	}
	public void addDetail(List<Details> details) {
		
		details.addAll(details);
	}

	public void removeDetail(Details detail) {
		details.remove(detail);
	}
	
	@PostLoad
	public void setPriceAfterDiscount() {
		if(price==null) {
			price=0.0;
		}
		if(discount==null) {
			discount=0.0;
		}
		priceAfterDiscount = price - price * discount / 100;
	}

	public SkuProductResponse mapToSkuProductResponseWithPrduct() {
	System.out.println(details.toString());
		return SkuProductResponse.builder().skuCode(skuCode)
				.product(product.mapToproductResponseWithCategoryBrandAndDepartment())
				.details(details)
				.imageUrls(imageUrls).priceAfterDiscount(priceAfterDiscount).price(price).discount(discount).currency(currency).build();
	}
	
	public SkuProductResponse mapToSkuProductResponseOutPrduct() {
		return SkuProductResponse.builder().skuCode(skuCode)
				.details(details)
				.imageUrls(imageUrls).price(price).discount(discount).currency(currency).priceAfterDiscount(priceAfterDiscount)

				.build();
	}
	

}
