package com.alrussy_dev.order_service.queries.services;

import com.alrussy_dev.order_service.common.PagedResult;
import com.alrussy_dev.order_service.queries.model.Order;
import com.alrussy_dev.order_service.queries.model.dto.Filter;
import com.alrussy_dev.order_service.queries.model.dto.OrderResponse;
import java.util.List;

public interface OrderService extends BaseService<String, OrderResponse, Order> {

    PagedResult<OrderResponse> findAll(int pageNumper);

    OrderResponse findByOrderNumber(String orderNumber);

    Integer countByUser(String userId);

    PagedResult<OrderResponse> find(Filter filter);

    List<OrderResponse> findByCustomerId(String customerID);
}
