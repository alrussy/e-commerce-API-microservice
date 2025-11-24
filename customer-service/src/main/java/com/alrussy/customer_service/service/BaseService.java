package com.alrussy.customer_service.service;

import java.util.List;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public interface BaseService<ID, DtoResponse, DtoRequest> {

    ID save(DtoRequest request);
	void deleteByIdAndUsername(Long id, String userName);
    List<DtoResponse> findByUsername(String username);



}
