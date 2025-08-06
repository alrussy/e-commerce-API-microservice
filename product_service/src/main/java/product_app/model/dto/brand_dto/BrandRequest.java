package product_app.model.dto.brand_dto;

import java.util.List;

public record BrandRequest(String name, String imageUrl, List<Long> categoryIds) {}
