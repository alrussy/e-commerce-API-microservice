package com.alrussy_dev.inventory_service.mapper;

import com.alrussy_dev.inventory_service.model.dto.PagedResult;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
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
