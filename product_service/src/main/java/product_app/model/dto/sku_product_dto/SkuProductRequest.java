package product_app.model.dto.sku_product_dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import product_app.model.entities.Details;

public record SkuProductRequest(
        @NotNull Long productId,
        @NotNull Long categoryId,
        @NotNull Double price,
        @NotNull Double discount,
        @NotNull String currency,
        Boolean isPrimary,
        @NotNull @NotEmpty List<String> imageUrls,
        @NotEmpty @NotNull List<Details> details) {}
