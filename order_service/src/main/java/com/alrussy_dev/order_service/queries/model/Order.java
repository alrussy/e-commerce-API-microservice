package com.alrussy_dev.order_service.queries.model;

import com.alrussy_dev.order_service.common.Address;
import com.alrussy_dev.order_service.common.LineProduct;
import com.alrussy_dev.order_service.common.OrderStatus;
import com.alrussy_dev.order_service.common.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
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
@Document(collection = "orders")
public class Order {

    @Id
    private String orderNumber;

    @NotBlank
    private String customerId;

    @NotBlank
    private LocalDateTime createdAt;

    @NotBlank
    private OrderStatus status;

    private Double amount;
    private Address address;
    private PaymentMethod paymentMethod;
    private Set<LineProduct> lineProducts = new HashSet<>();
    private Double taxFee;
    private Double shippingFee;

    public void addLineProduct(LineProduct lineProduct) {
        lineProducts.add(lineProduct);
    }

    public void removeLineProduct(LineProduct lineProduct) {
        lineProducts.remove(lineProduct);
    }
}
