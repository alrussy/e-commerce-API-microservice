package com.alrussy.product_service.model.dto.product_dto;

import com.alrussy.product_service.model.dto.details_dto.ProductDetailsRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@JsonInclude(value = Include.NON_NULL)
public record ProductRequest(
        @NotBlank(message = "name is blank it must be content one letter  ") String name,
        @NotNull Long categoryId,
        @NotNull Long departmentId,
        @NotNull Long brandId,
        @NotNull Long unitId,
        @NotBlank String imageUrl,
        String currency,
        Double price,
        Double discount,
        List<ProductDetailsRequest> details,
        String about) {}
