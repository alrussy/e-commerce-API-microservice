package com.alrussy.product_service.config;

import com.alrussy.product_service.ApplicationProperties;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Autowired
    ApplicationProperties properties;

    @Bean
    RestClient productClient() {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(Duration.ofSeconds(5L));
        simpleClientHttpRequestFactory.setReadTimeout(Duration.ofSeconds(5L));

        return RestClient.builder()
                .baseUrl(properties.inventoryServiceUrl() + "/inventory")
                .requestFactory(simpleClientHttpRequestFactory)
                .build();
    }
}
