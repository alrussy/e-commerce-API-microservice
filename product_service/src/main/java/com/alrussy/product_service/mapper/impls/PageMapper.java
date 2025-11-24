package com.alrussy.product_service.mapper.impls;

import com.alrussy.product_service.model.dto.PagedResult;
import org.springframework.data.domain.Page;

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
