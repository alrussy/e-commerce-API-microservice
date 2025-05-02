package product_app.model.dto.product_dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public record ProductFilter(String name,PriceFilter price,Long categoryId,Long brandId) {
	
	public record PriceFilter(Double price,TypeFilter type) {
		
		@AllArgsConstructor
		public enum TypeFilter {
			
			EQUEL("="),GREATE_THAN(">"),LESS_THAN("<"),GREATE_THAN_OR_EQUEL(">="),LESS_THAN_OR_EQUEL("<=");
			@Getter
			private final String operation; 

		}
	}
}

