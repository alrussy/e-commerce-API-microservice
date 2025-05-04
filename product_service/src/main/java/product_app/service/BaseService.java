package product_app.service;

import jakarta.persistence.MappedSuperclass;
import product_app.model.dto.PageResult;

@MappedSuperclass
public interface BaseService<ID, DtoResponse, DtoRequest> {

    PageResult<DtoResponse> findAll(int pageNum);

    DtoResponse findById(ID id);

    void delete(ID id);

    DtoResponse save(DtoRequest request);

    DtoResponse update(ID id, DtoRequest request) throws Exception;

    Long count();
}
