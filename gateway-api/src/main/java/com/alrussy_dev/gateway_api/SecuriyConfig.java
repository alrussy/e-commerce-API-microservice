package com.alrussy_dev.gateway_api;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.header.writers.CrossOriginResourcePolicyHeaderWriter.CrossOriginResourcePolicy;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.header.CrossOriginOpenerPolicyServerHttpHeadersWriter.CrossOriginOpenerPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebFluxSecurity
public class SecuriyConfig {

	@Bean
	SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {

		http.csrf(ServerHttpSecurity.CsrfSpec::disable).cors(c->c.disable())
				.authorizeExchange(auth -> auth.pathMatchers(
						"/product/swagger-ui/**",
						"/notification/swagger-ui/**",
						"/inventory/swagger-ui/**",
						"/order/swagger-ui/**",
						"/product/v3/api-docs/**",
						"/notification/v3/api-docs/**",
						"/inventory/v3/api-docs/**",
						"/order/v3/api-docs/**",
						"/v3/api-docs/**"
						)
						.permitAll()
						.anyExchange().authenticated())
				.oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()));

		return http.build();

	}
//
//	@Bean
//	CorsConfigurationSource configurationSource() {
//		CorsConfiguration configuration = new CorsConfiguration();
//		System.out.println(configuration.getAllowedOrigins());
//		configuration.setAllowedOrigins(List.of("http://localhost:5173"));
//		System.out.println(configuration.getAllowedOrigins());
//
//		//		
//		configuration.setAllowCredentials(true);
//		configuration.setAllowedMethods(List.of("GET","POST"));  
//		configuration.setAllowedHeaders(List.of("Authorization"));  
//		configuration.setMaxAge(3600L);
//		configuration.setAllowPrivateNetwork(true);
//		UrlBasedCorsConfigurationSource basedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
//		
//		basedCorsConfigurationSource.setCorsConfigurations(Map.of("/**", configuration));
//		System.out.println(basedCorsConfigurationSource);
//
//		return basedCorsConfigurationSource;
//	}

}
