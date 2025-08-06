package com.alrussy_dev.order_service.config;

import com.alrussy_dev.order_service.ApplicationProperties;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Autowired
    ApplicationProperties properties;

    @Bean
    @Qualifier("productClient")
    RestClient productClient() {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(Duration.ofSeconds(5L));
        simpleClientHttpRequestFactory.setReadTimeout(Duration.ofSeconds(5L));

        return RestClient.builder()
                .baseUrl(properties.productServiceUrl() + "/api/products")
                .requestFactory(simpleClientHttpRequestFactory)
                .build();
    }
}
