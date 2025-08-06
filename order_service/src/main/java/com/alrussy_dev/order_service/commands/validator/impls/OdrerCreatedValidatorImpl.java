package com.alrussy_dev.order_service.commands.validator.impls;

import com.alrussy_dev.order_service.client.product.ProductService;
import com.alrussy_dev.order_service.client.product.model.SkuProduct;
import com.alrussy_dev.order_service.commands.validator.OrderCreatedValidator;
import com.alrussy_dev.order_service.queries.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Primary
public class OdrerCreatedValidatorImpl implements OrderCreatedValidator {

    private final ProductService productService;

    @Override
    public void validate(Order order) {
        order.getCheckout().getLineProducts().parallelStream().forEach(lineProduct -> {
            SkuProduct skuProduct = productService.getProduct(lineProduct.getSkuCode());
            if (skuProduct == null) throw new RuntimeException("Product is not found ");
            else {

                if (skuProduct.price().doubleValue() != lineProduct.getPrice().doubleValue())
                    throw new RuntimeException("can not create order becouse there price invalid ");
            }
        });
    }
}
