package com.alrussy_dev.inventory_service.model.dto;

import jakarta.persistence.Embeddable;

@Embeddable
public record OpeningStock(String skuCode, Integer openingStock) {}
