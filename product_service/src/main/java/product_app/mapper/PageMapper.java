package product_app.mapper;

import org.springframework.data.domain.Page;
import product_app.model.dto.PageResponse;

public class PageMapper<T> {

    public PageResponse<T> toPageResponse(Page<T> page) {
        return new PageResponse<T>(
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
