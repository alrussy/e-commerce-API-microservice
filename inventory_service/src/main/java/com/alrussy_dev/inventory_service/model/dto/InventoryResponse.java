package com.alrussy_dev.inventory_service.model.dto;

import com.alrussy_dev.inventory_service.client.SkuProduct;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;

@JsonInclude(value = Include.NON_NULL)
public record InventoryResponse(
        SkuProduct skuProduct,
        Integer openingStock,
        Integer incoming,
        Integer outoing,
        Integer stock,
        List<ActionForItemResponse> actions) {}
