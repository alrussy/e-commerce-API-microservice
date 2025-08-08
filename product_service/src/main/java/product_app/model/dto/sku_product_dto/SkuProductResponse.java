package product_app.model.dto.sku_product_dto;

import java.util.List;
import java.util.Map;
import product_app.model.dto.product_dto.ProductResponse;

public record SkuProductResponse(
        String skuCode,
        ProductResponse product,
        Map<String,String> details,
        List<String> imageUrls,
        Double price,
        Double discount,
        String currency,
        Double priceAfterDiscount) {}
