package com.alrussy_dev.order_service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventStore extends MongoRepository<OrderEvent, String> {

    List<OrderEvent> findByAggregateId(String aggragateId, Sort ascending);

    List<OrderEvent> findByCustomerId(String customerId, Sort descending);
}
