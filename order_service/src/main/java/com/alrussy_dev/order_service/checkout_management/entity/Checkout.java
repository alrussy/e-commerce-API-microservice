package com.alrussy_dev.order_service.checkout_management.entity;

import com.alrussy_dev.order_service.common.Address;
import com.alrussy_dev.order_service.common.CheckoutStatus;
import com.alrussy_dev.order_service.common.PaymentMethod;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document
public class Checkout {

    @Id
    private String id;

    private String cartId;
    private Address address;
    private PaymentMethod paymentMethod;
    private CheckoutStatus status;
    private String payId;
}
