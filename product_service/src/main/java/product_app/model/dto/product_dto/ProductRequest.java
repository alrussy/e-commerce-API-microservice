package product_app.model.dto.product_dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRequest(
        @NotBlank(message = "name is blank it must be content one letter  ") String name,
        Boolean isFeature,
        @NotNull Long categoryId,
        @NotNull Long departmentId,
        @NotNull Long brandId,
        @NotBlank String imageUrl,
        String about) {}
