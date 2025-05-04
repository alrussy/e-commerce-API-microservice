package product_app.service;

import jakarta.persistence.MappedSuperclass;
import product_app.model.dto.PagedResult;

@MappedSuperclass
public interface BaseService<ID, DtoResponse, DtoRequest> {

    PagedResult<DtoResponse> findAll(int pageNum);

    DtoResponse findById(ID id);

    void delete(ID id);

    DtoResponse save(DtoRequest request);

    DtoResponse update(ID id, DtoRequest request) throws Exception;

    Long count();
}
