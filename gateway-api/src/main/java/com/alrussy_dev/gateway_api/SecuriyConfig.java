package com.alrussy_dev.gateway_api;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.AddRequestHeaderGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.cloud.gateway.filter.headers.observation.GatewayContext;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.support.tagsprovider.GatewayRouteTagsProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.ReactiveHttpInputMessage;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.web.reactive.function.OAuth2BodyExtractors;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity

public class SecuriyConfig {
    @Bean
    SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {

        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(c->c.configurationSource(configurationSource()))  
                
                .authorizeExchange(auth -> auth.pathMatchers(
                                "/product/swagger-ui/**",
                                "/notification/swagger-ui/**",
                                "/inventory/swagger-ui/**",
                                "/order/swagger-ui/**",
                                "/product/v3/api-docs/**",
                                "/notification/v3/api-docs/**",
                                "/inventory/v3/api-docs/**",
                                "/order/v3/api-docs/**",
                                "/v3/api-docs/**").permitAll()
                        .anyExchange().authenticated())
                       
                .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()));
          return http.build();
    }
    
//    @Bean
//    public GlobalFilter customFilter() {
//        return new CustomGlobalFilter();
//    }
//
//    static public class CustomGlobalFilter implements GlobalFilter, Ordered {
//
//   
//        @Override
//        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        var token=exchange.getRequest().getHeaders().get("A"+"UTHORIZATION".toLowerCase()).toString();
//        
//    	System.out.println(token.toString().substring(7,token.length()-1));
//
//       	exchange.getRequest().getHeaders().add("X_USER_NAME","" );
//        	System.out.println(exchange.getRequest().getHeaders());
//        
//            return chain.filter(exchange);
//        }
//
//        @Override
//        public int getOrder() {
//            return -1;
//        }
//    }

       	@Bean
    	CorsConfigurationSource configurationSource() {
    		CorsConfiguration configuration = new CorsConfiguration();
    		configuration.setAllowCredentials(true);
    		configuration.setAllowedOrigins(List.of("http://localhost:5173"));
    		configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE"));
    		configuration.setAllowedHeaders(List.of("Authorization","Content-Type"));
    		configuration.setMaxAge(3600L);
    		configuration.setAllowPrivateNetwork(true);
    		UrlBasedCorsConfigurationSource basedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
    		basedCorsConfigurationSource.setCorsConfigurations(Map.of("/**", configuration));
    		System.out.println(basedCorsConfigurationSource);
    
    		return basedCorsConfigurationSource;
    	}

}
