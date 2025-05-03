package product_app.model.dto;

import java.util.List;

public record PageResponse<T>(
        List<T> data,
        long totalElements,
        int pageNumber,
        int totalPage,
        boolean isFirst,
        boolean isLast,
        boolean hasNext,
        boolean hasPrevious) {}
