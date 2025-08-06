package com.alrussy_dev.order_service.commands.repository;

import com.alrussy_dev.order_service.commands.model.OrderEvent;
import com.alrussy_dev.order_service.commands.model.enums.EventType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderEventRepository extends MongoRepository<OrderEvent, String> {

    List<OrderEvent> findByOrderNumber(String orderNumber);

    Optional<OrderEvent> findByOrderNumberAndEventType(String orderNumber, EventType eventType);

    boolean existsByOrderNumberAndEventType(String orderNumber, EventType eventType);

    List<OrderEvent> findByPublished(boolean b, Sort ascending);

    List<OrderEvent> findByOrderNumber(String orderNumber, Sort ascending);
}
