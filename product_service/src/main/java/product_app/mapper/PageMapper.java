package product_app.mapper;

import org.springframework.data.domain.Page;
import product_app.model.dto.PagedResult;

public class PageMapper<T> {

    public PagedResult<T> toPageResponse(Page<T> page) {
        return new PagedResult<T>(
                page.getContent(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious());
    }
}
