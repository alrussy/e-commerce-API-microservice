package com.alrussy.product_service.model.dto.details_dto;

import java.util.Set;

public record ProductDetailsRequest(String name, Set<String> values) {}
