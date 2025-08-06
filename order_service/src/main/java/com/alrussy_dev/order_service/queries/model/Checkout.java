package com.alrussy_dev.order_service.queries.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Checkout {

	@Id
	private String id;
    private Long addressId;
    private Double discount;
    private Double shippingFee;
    private Double taxFee;
    private String paymentMethod;
    private String paymentId;
    private String deliveryType;
    private List<LineProduct> lineProducts;
}
