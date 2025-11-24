package com.alrussy_dev.inventory_service.config;

import com.alrussy_dev.inventory_service.ApplicationProperties;
import com.alrussy_dev.inventory_service.model.dto.Event;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.BatchMessagingMessageConverter;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@RequiredArgsConstructor
public class kafkaConfig {

    private final ApplicationProperties properties;

    private Map<String, Object> baseProberty() {
        String bootstrapServers = "localhost:9092";
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "inventory-service");
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());

        return props;
    }

    @Bean
    ConsumerFactory<String, Event> kafkaConsumerForOpeningStock() {

        Map<String, Object> props = baseProberty();
        props.put(
                JsonDeserializer.VALUE_DEFAULT_TYPE,
                "com.alrussy_dev.inventory_service.model.dto.Event.OpeningStockEvent");
        props.put(JsonDeserializer.TYPE_MAPPINGS, "inventory:com.alrussy_dev.inventory_service.model.dto.Event");
        return new DefaultKafkaConsumerFactory<>(props, null, new JsonDeserializer<Event>());
    }

    @Bean
    ConsumerFactory<String, Event> kafkaConsumerForOrder() {

        Map<String, Object> props = baseProberty();
        props.put(
                JsonDeserializer.VALUE_DEFAULT_TYPE,
                "com.alrussy_dev.inventory_service.model.dto.Event.CreatedOrderEvent");
        props.put(JsonDeserializer.TYPE_MAPPINGS, "inventory:com.alrussy_dev.inventory_service.model.dto.Event");
        return new DefaultKafkaConsumerFactory<>(props, null, new JsonDeserializer<Event>());
    }

    @Bean
    JsonMessageConverter converter() {
        return new JsonMessageConverter();
    }

    @Bean
    KafkaListenerContainerFactory<?> kafkaListenerForOpeningStock() {
        ConcurrentKafkaListenerContainerFactory<String, Event> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(kafkaConsumerForOpeningStock());
        factory.setRecordMessageConverter(new JsonMessageConverter());
        factory.setBatchListener(true);
        factory.setBatchMessageConverter(new BatchMessagingMessageConverter(converter()));

        return factory;
    }

    @Bean
    KafkaListenerContainerFactory<?> kafkaListenerforOrder() {
        ConcurrentKafkaListenerContainerFactory<String, Event> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(kafkaConsumerForOrder());
        factory.setRecordMessageConverter(new JsonMessageConverter());
        factory.setBatchListener(true);
        factory.setBatchMessageConverter(new BatchMessagingMessageConverter(converter()));

        return factory;
    }
}
