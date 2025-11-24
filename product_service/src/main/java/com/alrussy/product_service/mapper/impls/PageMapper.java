package com.alrussy.product_service.mapper.impls;

import org.springframework.data.domain.Page;

import com.alrussy.product_service.model.dto.PagedResult;

public class PageMapper<T> {

    public PagedResult<T> toPageResponse(Page<T> page) {
        return new PagedResult<T>(
                page.getContent(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber() + 1,
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious());
    }
}
