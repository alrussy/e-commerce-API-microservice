package com.alrussy_dev.notification_service.repository;

import com.alrussy_dev.notification_service.entity.NotificationOrderState;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationOrderStateRepository extends MongoRepository<NotificationOrderState, String> {

    List<NotificationOrderState> findByIsSent(boolean b);

    List<NotificationOrderState> findByUserInfoUserId(String userId);
}
