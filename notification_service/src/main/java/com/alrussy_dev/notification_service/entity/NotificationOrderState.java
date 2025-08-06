package com.alrussy_dev.notification_service.entity;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class NotificationOrderState {

    @Id
    private String id;

    private String orderNumper;
    private EventType orderState;
    private String content;
    private Boolean isSent;
    private Instant sendDateAt;
    private UserInfo userInfo;
}
