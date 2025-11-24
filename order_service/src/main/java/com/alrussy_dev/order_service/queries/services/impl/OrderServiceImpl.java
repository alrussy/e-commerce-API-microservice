package com.alrussy_dev.order_service.queries.services.impl;

import com.alrussy_dev.order_service.common.PageMapper;
import com.alrussy_dev.order_service.common.PagedResult;
import com.alrussy_dev.order_service.queries.mapper.impls.OrderMapper;
import com.alrussy_dev.order_service.queries.model.Order;
import com.alrussy_dev.order_service.queries.model.dto.Filter;
import com.alrussy_dev.order_service.queries.model.dto.OrderResponse;
import com.alrussy_dev.order_service.queries.repository.OrderRepository;
import com.alrussy_dev.order_service.queries.services.OrderService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final PageMapper<OrderResponse> pageMapper;
    private final OrderRepository repository;
    private final MongoTemplate mongoTemplate;

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
    public List<OrderResponse> findByCustomerId(String customerId) {
        var listResponse = repository.findByCustomerId(customerId).stream()
                .map(order -> orderMapper.fromEntity(order))
                .filter(t -> t.status() != null)
                .toList();
        return listResponse;
    }

    @Override
    public Integer countByUser(String userId) {
        return repository.countByCustomerId(userId);
    }

    @Override
    public PagedResult<OrderResponse> find(Filter filter) {
        Pageable pageable = PageRequest.of(
                filter.pageNumber() <= 1 ? 0 : filter.pageNumber() - 1,
                10,
                Sort.by(Arrays.asList(filter.sortedby()).stream()
                        .map(s -> filter.direction().equalsIgnoreCase("asc")
                                ? org.springframework.data.domain.Sort.Order.asc(getFieldName(s))
                                : org.springframework.data.domain.Sort.Order.desc(getFieldName(s)))
                        .toList()));

        Query query = new Query().with(pageable);
        List<Criteria> criterias = new ArrayList<>();

        if (filter.orderNumber() != null) {
            criterias.add(Criteria.where("orderNumber").is(filter.orderNumber()));
        }
        if (filter.status() != null) {
            criterias.add(Criteria.where("status").is(filter.status()));
        }

        if (filter.skuCode() != null) {
            criterias.add(Criteria.where("checkout.lineProducts.skuCode").is(filter.skuCode()));
        }

        if (filter.fromDate() != null || filter.toDate() != null) {
            // criterias.add(Criteria.where("date").is(filter.date()));
            System.out.println();
            criterias.add(Criteria.where("date").gte(filter.fromDate()).lte(filter.toDate()));
        }
        if (filter.customerId() != null) {

            criterias.add(Criteria.where("customerId").is(filter.customerId()));
        }

        //	if(filter.amountEqual()!= null) {
        //		criterias.add(Criteria.where("amount").is(filter.amountEqual()));
        //	}
        //	else if(filter.amountLessThan()!= null) {
        //		criterias.add(Criteria.where("amount").lt(filter.amountLessThan()));
        //	}
        //	else if(filter.amountLessThanOrEqual()!= null) {
        //		criterias.add(Criteria.where("amount").lte(filter.amountLessThanOrEqual()));
        //	}
        //
        //	else if(filter.amountGreateThanOrEqual()!= null) {
        //		criterias.add(Criteria.where("amount").gte(filter.amountGreateThanOrEqual()));
        //	}
        //
        //	else if(filter.amountGreateThan()!= null) {
        //		criterias.add(Criteria.where("amount").gt(filter.amountGreateThan()));
        //	}

        if (!criterias.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criterias.toArray(new Criteria[0])));
        }

        Page<Order> page = PageableExecutionUtils.getPage(
                mongoTemplate.find(query, Order.class),
                pageable,
                () -> mongoTemplate.count(query.skip(0).limit(0), Order.class));

        return pageMapper.toPageResponse(page.map(orderMapper::fromEntity));
    }

    private String getFieldName(String content) {
        if (content.toLowerCase().startsWith("customer")) {
            return "userId";
        } else return content;
    }
}
