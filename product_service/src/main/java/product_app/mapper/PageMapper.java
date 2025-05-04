package product_app.mapper;

import org.springframework.data.domain.Page;
import product_app.model.dto.PageResult;

public class PageMapper<T> {

    public PageResult<T> toPageResponse(Page<T> page) {
        return new PageResult<T>(
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
