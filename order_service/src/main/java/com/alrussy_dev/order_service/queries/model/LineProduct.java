package com.alrussy_dev.order_service.queries.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LineProduct {

	@Id
	private String  id;

    @NotBlank
    private String skuCode;

    @Min(1)
    private Double price;

    @Min(0)
    private Double discount;

    @NotBlank
    private String curruncy;

    @Min(1)
    private Integer quentity;
}
