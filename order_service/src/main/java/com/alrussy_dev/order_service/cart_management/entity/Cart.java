package com.alrussy_dev.order_service.cart_management.entity;

import com.alrussy_dev.order_service.common.CartStatus;
import com.alrussy_dev.order_service.common.LineProduct;
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
@Document
public class Cart {

    @Id
    private String id;

    private String customerId;
    private LocalDateTime createdAt;
    private CartStatus status;
    private Set<LineProduct> lineProducts = new HashSet<LineProduct>();
    private String checkoutId;

    public void addLineProduct(LineProduct lineProduct) {
        lineProducts.add(lineProduct);
    }

    public void removeLineProduct(LineProduct lineProduct) {
        lineProducts.remove(lineProduct);
    }
}
