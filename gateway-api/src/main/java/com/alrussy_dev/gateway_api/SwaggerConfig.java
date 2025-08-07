package com.alrussy_dev.gateway_api;

import jakarta.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String DEFAULT_API_DOCS_URL = "/v3/api-docs";
    private final RouteDefinitionLocator routeDefinitionLocator;
    private final SwaggerUiConfigProperties swaggerProperties;

    public SwaggerConfig(RouteDefinitionLocator routeDefinitionLocator, SwaggerUiConfigProperties swaggerProperties) {
        this.routeDefinitionLocator = routeDefinitionLocator;
        this.swaggerProperties = swaggerProperties;
    }

    @PostConstruct
    void init() {

        List<RouteDefinition> routeDefinitions =
                routeDefinitionLocator.getRouteDefinitions().collectList().block();
        if (routeDefinitions == null) {
            return;
        }
        Set<AbstractSwaggerUiConfigProperties.SwaggerUrl> swaggerUrls = new HashSet<>();
        routeDefinitions.stream()
                .filter(routeDefinition -> routeDefinition.getId().matches(".*-service"))
                .forEach(routeDefinition -> {
                    String serviceName = routeDefinition.getId().replace("-service", "");
                    AbstractSwaggerUiConfigProperties.SwaggerUrl swaggerUrl =
                            new AbstractSwaggerUiConfigProperties.SwaggerUrl(
                                    serviceName, DEFAULT_API_DOCS_URL + "/" + serviceName, null);
                    swaggerUrls.add(swaggerUrl);
                });
        System.out.println(swaggerUrls);
        swaggerProperties.setUrls(swaggerUrls);
    }
}
