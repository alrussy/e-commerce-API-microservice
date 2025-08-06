package product_app.mapper.impls;

import org.springframework.data.domain.Page;
import product_app.model.dto.PagedResult;

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
