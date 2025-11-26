package com.alrussy_dev.procurement_service.service;

import com.alrussy_dev.procurement_service.dto.PagedResult;

public interface BaseService<ID, DtoResponse, DtoRequest> {

    PagedResult<DtoResponse> findAll(int pageNum);

    DtoResponse findById(ID id);

    Long delete(ID id);

    DtoResponse save(DtoRequest request);

    DtoResponse update(ID id, DtoRequest request) throws Exception;

    Long count();
}
