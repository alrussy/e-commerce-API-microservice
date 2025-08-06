package com.alrussy_dev.order_service.client.product;

import com.alrussy_dev.order_service.client.product.model.SkuProduct;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ProductService {
    private RestClient client;

    public ProductService(@Qualifier("productClient") RestClient client) {
        this.client = client;
        // TODO Auto-generated constructor stub
    }

    public List<SkuProduct> getProducts(List<String> skuCodes) {
        var skuClint = client.get()
                .uri("/sku/skuCodes", Map.of("skuCodes", skuCodes))
                .retrieve()
                .toEntity(SkuProduct[].class);
        List<SkuProduct> skuProducts = Arrays.asList(skuClint.getBody());
        return skuProducts;
    }

    @CircuitBreaker(name = "product-service", fallbackMethod = "fallbackMethod")
    public SkuProduct getProduct(String skuCode) {

        return client.get()
                .uri("/sku/{skuCode}", skuCode)
                .retrieve()
                .toEntity(SkuProduct.class)
                .getBody();
    }

    void fallbackMethod() {}
}
