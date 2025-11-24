package com.alrussy_dev.order_service.config;

import com.alrussy_dev.order_service.ApplicationProperties;
import com.alrussy_dev.order_service.Event;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaAdmin.NewTopics;
import org.springframework.kafka.support.converter.BatchMessagingMessageConverter;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class KafkaConfiguration {

    @Autowired
    ApplicationProperties properties;

    @Bean
    NewTopics topic() {
        return new NewTopics(
                new NewTopic(properties.topicEventHandler(), 1, (short) 1),
                new NewTopic(properties.topicShippedOrder(), 1, (short) 1),
                new NewTopic(properties.topicDeliveredOrder(), 1, (short) 1),
                new NewTopic(properties.topicConfirmedOrder(), 1, (short) 1),
                new NewTopic(properties.topicOrderCancelled(), 1, (short) 1),
                new NewTopic(properties.topicOrderError(), 1, (short) 1),
                new NewTopic(properties.topicOpenedNewCart(), 1, (short) 1),
                new NewTopic(properties.topicAddedProductToCart(), 1, (short) 1),
                new NewTopic(properties.topicRemovedProductFromCart(), 1, (short) 1),
                new NewTopic(properties.topicChangedQuantity(), 1, (short) 1),
                new NewTopic(properties.topicStartedCheckout(), 1, (short) 1),
                new NewTopic(properties.topicCreatedOrder(), 1, (short) 1),
                new NewTopic("", 1, (short) 1));
    }

    @Bean
    ConsumerFactory<String, Event> kafkaConsumerFactory2() {
        String bootstrapServers = "localhost:9092";
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.alrussy_dev.order_service.Event");
        props.put(JsonDeserializer.TYPE_MAPPINGS, "order:com.alrussy_dev.order_service.Event");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());

        return new DefaultKafkaConsumerFactory<>(props, null, new JsonDeserializer<>(Event.class));
    }

    @Bean
    KafkaListenerContainerFactory<?> kafkaListenerContainerFactory2() {
        ConcurrentKafkaListenerContainerFactory<String, Event> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(kafkaConsumerFactory2());
        factory.setRecordMessageConverter(new JsonMessageConverter());
        factory.setBatchListener(true);
        factory.setBatchMessageConverter(new BatchMessagingMessageConverter(converter()));

        return factory;
    }

    @Bean
    JsonMessageConverter converter() {
        return new JsonMessageConverter();
    }
}
