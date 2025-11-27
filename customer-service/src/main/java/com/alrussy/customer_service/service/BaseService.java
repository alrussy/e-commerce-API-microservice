package com.alrussy.customer_service.service;

import jakarta.persistence.MappedSuperclass;
import java.util.List;

@MappedSuperclass
public interface BaseService<ID, DtoResponse, DtoRequest> {

    ID save(DtoRequest request);

    void deleteByIdAndUsername(Long id, String userName);

    List<DtoResponse> findByUsername(String username);
}
