package product_app;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "products")
public record ApplicationProperties(String inventoryServiceUrl) {}
