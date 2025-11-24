package com.alrussy_dev.order_service.queries.mapper.impls;

import com.alrussy_dev.order_service.queries.model.Order;
import com.alrussy_dev.order_service.queries.model.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
@RequiredArgsConstructor
public class OrderMapper {

    public OrderResponse fromEntitySymmary(Order entity) {
        return new OrderResponse(
                entity.getOrderNumber(),
                entity.getCreatedAt(),
                entity.getCustomerId(),
                entity.getStatus(),
                "$",
                entity.getAmount(),
                null,
                null,
                null,
                null,
                null);
    }

    public OrderResponse fromEntity(Order entity) {
        return new OrderResponse(
                entity.getOrderNumber(),
                entity.getCreatedAt(),
                entity.getCustomerId(),
                entity.getStatus(),
                "$",
                entity.getLineProducts().stream()
                        .mapToDouble(line ->
                                (line.getPrice() - (line.getPrice() * line.getDiscount() / 100)) * line.getQuantity())
                        .sum(),
                entity.getAddress(),
                entity.getPaymentMethod(),
                entity.getLineProducts(),
                entity.getTaxFee(),
                entity.getShippingFee());
    }
}
