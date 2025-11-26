package com.alrussy_dev.procurement_service.config;

import com.alrussy_dev.procurement_service.ApplicationProperties;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin.NewTopics;

@Configuration
public class KafkaConfig {

    @Autowired
    ApplicationProperties properties;

    @Bean
    NewTopics topic() {
        return new NewTopics(
                new NewTopic(properties.cancelledInvoiceTopic(), 1, (short) 1),
                new NewTopic(properties.pendedInvoiceTopic(), 1, (short) 1),
                new NewTopic(properties.receivedInvoiceTopic(), 1, (short) 1),
                new NewTopic(properties.updatedInvoiceTopic(), 1, (short) 1));
    }
}
