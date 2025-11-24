package com.alrussy.product_service.service;

import com.alrussy.product_service.model.dto.PagedResult;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public interface BaseService<ID, DtoResponse, DtoRequest> {

    PagedResult<DtoResponse> findAll(int pageNum);

    DtoResponse findById(ID id);

    void delete(ID id);

    ID save(DtoRequest request);

    DtoResponse update(ID id, DtoRequest request) throws Exception;

    Long count();
}
