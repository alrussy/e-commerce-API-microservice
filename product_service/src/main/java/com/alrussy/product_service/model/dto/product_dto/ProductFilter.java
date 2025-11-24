package com.alrussy.product_service.model.dto.product_dto;

public record ProductFilter(String name, Long groupId, Long categoryId, Long brandId, Long departmentId) {}
