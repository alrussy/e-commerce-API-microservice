package com.alrussy.product_service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthConverter jwtAuthConverter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());
        http.cors(c -> c.disable());
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll();
            auth.requestMatchers(HttpMethod.GET).permitAll();
            auth.anyRequest().authenticated();
        });
        http.oauth2ResourceServer(oauth -> oauth.jwt(config -> config.jwtAuthenticationConverter(jwtAuthConverter)));
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
