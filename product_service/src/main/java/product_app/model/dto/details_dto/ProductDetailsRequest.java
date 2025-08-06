package product_app.model.dto.details_dto;

import java.util.Set;

public record ProductDetailsRequest(String name, Set<String> values) {}
