package com.alrussy_dev.procurement_service.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LineProduct {

    @NotBlank
    private String skuCode;

    @Min(1)
    private Double price;

    @Min(0)
    private Double discount;

    @NotBlank
    private String curruncy;

    @Min(1)
    private Integer quantity;

    private String unit;
}
