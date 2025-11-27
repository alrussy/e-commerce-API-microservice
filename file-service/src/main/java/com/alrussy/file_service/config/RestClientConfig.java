package com.alrussy.file_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    RestClient client() {
        return RestClient.builder().baseUrl("http://localhost:9191").build();
    }
}
