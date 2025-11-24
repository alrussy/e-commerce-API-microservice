package com.alrussy.product_service.model.dto;

import java.util.List;

public record PagedResult<T>(
        List<T> data,
        long totalElements,
        int totalPage,
        int pageNumber,
        boolean isFirst,
        boolean isLast,
        boolean hasNext,
        boolean hasPrevious) {}
