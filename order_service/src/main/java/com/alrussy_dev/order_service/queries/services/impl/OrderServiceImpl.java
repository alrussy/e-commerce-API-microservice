package com.alrussy_dev.order_service.queries.services.impl;

import com.alrussy_dev.order_service.queries.mapper.OrderMapper;
import com.alrussy_dev.order_service.queries.mapper.impls.PageMapper;
import com.alrussy_dev.order_service.queries.model.Order;
import com.alrussy_dev.order_service.queries.model.dto.OrderResponse;
import com.alrussy_dev.order_service.queries.model.dto.PagedResult;
import com.alrussy_dev.order_service.queries.repository.OrderRepository;
import com.alrussy_dev.order_service.queries.services.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final PageMapper<OrderResponse> pageMapper;
    private final OrderRepository repository;

    @Override
    public PagedResult<OrderResponse> findAll(int pageNumber) {
        var pageaple = PageRequest.of(pageNumber <= 1 ? 0 : pageNumber - 1, 10);
        return pageMapper.toPageResponse(repository.findAll(pageaple).map(orderMapper::fromEntity));
    }

    @Override
    public OrderResponse findByOrderNumber(String orderNumber) {
        Order order = repository
                .findById(orderNumber)
                .orElseThrow(() -> new RuntimeException("order not found by id: " + orderNumber));
        return orderMapper.fromEntity(order);
    }

    @Override
    public List<OrderResponse> findByUser(String userId) {
        var listResponse = repository.findByUserId(userId).stream()
                .map(order -> orderMapper.fromEntity(order))
                .toList();
        return listResponse;
    }

    @Override
    public Integer countByUser(String userId) {
        return repository.countByUserId(userId);
    }
}
