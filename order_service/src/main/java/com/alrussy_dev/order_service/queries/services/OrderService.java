package com.alrussy_dev.order_service.queries.services;

import com.alrussy_dev.order_service.queries.model.dto.OrderRequest;
import com.alrussy_dev.order_service.queries.model.dto.OrderResponse;
import com.alrussy_dev.order_service.queries.model.dto.PagedResult;
import java.util.List;

public interface OrderService extends BaseService<String, OrderResponse, OrderRequest> {

    PagedResult<OrderResponse> findAll(int pageNumper);

    OrderResponse findByOrderNumber(String orderNumber);

    List<OrderResponse> findByUser(String userId);

    Integer countByUser(String userId);
}
