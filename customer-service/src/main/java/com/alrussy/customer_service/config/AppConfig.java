package com.alrussy.customer_service.config;

import com.alrussy.customer_service.audition.AppAuditing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AppConfig {

    @Bean
    AuditorAware<String> auditorAware() {
        return new AppAuditing();
    }
}
