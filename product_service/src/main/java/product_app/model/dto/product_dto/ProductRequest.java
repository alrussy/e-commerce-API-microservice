package product_app.model.dto.product_dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import product_app.model.dto.details_dto.ProductDetailsRequest;

public record ProductRequest(
        @NotBlank(message = "name is blank it must be content one letter  ") String name,
        @NotNull Long categoryId,
        @NotNull Long departmentId,
        @NotNull Long brandId,
        @NotBlank String imageUrl,
        List<ProductDetailsRequest> details,
        String about) {}
