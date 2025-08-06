package com.alrussy_dev.order_service.queries.mapper.impls;

import com.alrussy_dev.order_service.queries.mapper.CheckoutMapper;
import com.alrussy_dev.order_service.queries.mapper.OrderMapper;
import com.alrussy_dev.order_service.queries.model.Order;
import com.alrussy_dev.order_service.queries.model.dto.LineProductResponse;
import com.alrussy_dev.order_service.queries.model.dto.OrderRequest;
import com.alrussy_dev.order_service.queries.model.dto.OrderResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
@RequiredArgsConstructor
public class OrderMapperImpl implements OrderMapper {

    private final CheckoutMapper checkoutMapper;

    @Override
    public Order toEntity(OrderRequest request) {
        return Order.builder()
                .checkout(checkoutMapper.toEntity(request.checkout()))
                .build();
    }

    @Override
    public OrderResponse fromEntity(Order entity) {
        return new OrderResponse(
                entity.getOrderNumber(),
                entity.getCreatedAt(),
                entity.getUserId(),
                entity.getStatus(),
                checkoutMapper.fromEntity(entity.getCheckout()));
    }

    @Override
    public OrderResponse fromEntity(Order entity, List<LineProductResponse> lineProducts) {
        return null;
    }
}
