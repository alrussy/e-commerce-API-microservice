package com.alrussy.product_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import com.alrussy.product_service.audition.AppAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableWebSecurity
public class AppConfig {

    @Bean
    AuditorAware<String> auditorAware() {
        return new AppAuditing();
    }
}
