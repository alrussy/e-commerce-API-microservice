package com.alrussy_dev.order_service.queries.mapper;

import com.alrussy_dev.order_service.queries.model.Order;
import com.alrussy_dev.order_service.queries.model.dto.LineProductResponse;
import com.alrussy_dev.order_service.queries.model.dto.OrderRequest;
import com.alrussy_dev.order_service.queries.model.dto.OrderResponse;
import java.util.List;

public interface OrderMapper extends BaseMapper<Order, OrderResponse, OrderRequest> {

    OrderResponse fromEntity(Order entity, List<LineProductResponse> lineProduct);
}
