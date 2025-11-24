package com.alrussy_dev.order_service.queries.repository;

import com.alrussy_dev.order_service.common.OrderStatus;
import com.alrussy_dev.order_service.queries.model.Order;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByCustomerId(String customerId);

    Integer countByCustomerId(String userId);

    List<Order> findByStatus(OrderStatus status);
}
