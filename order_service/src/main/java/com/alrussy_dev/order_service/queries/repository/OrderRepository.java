package com.alrussy_dev.order_service.queries.repository;

import com.alrussy_dev.order_service.queries.model.Order;
import com.alrussy_dev.order_service.queries.model.enums.OrderStatus;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByUserId(String userId);

    Integer countByUserId(String userId);

    List<Order> findByStatus(OrderStatus status);
}
