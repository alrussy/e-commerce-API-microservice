package com.alrussy_dev.inventory_service.client;

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

    public SkuProduct getProducts(String skuCode) {
        try {
            var skuClint = client.get().uri("/sku/" + skuCode).retrieve().toEntity(SkuProduct.class);
            SkuProduct skuProduct = skuClint.getBody();
            return skuProduct;
        } catch (Exception e) {
            return new SkuProduct(
                    skuCode, new Product(0L, "none", "", null, null, null, ""), null, null, null, null, "", null);
        }
    }
    //
    //    @CircuitBreaker(name = "product-service", fallbackMethod = "fallbackMethod")
    //    public SkuProduct getProduct(String skuCode) {
    //
    //        System.out.println(skuCode);
    //        return client.get()
    //                .uri("/sku/{skuCode}", skuCode)
    //                .retrieve()
    //                .toEntity(SkuProduct.class)
    //                .getBody();
    //    }
    //
    //    void fallbackMethod() {}
}
