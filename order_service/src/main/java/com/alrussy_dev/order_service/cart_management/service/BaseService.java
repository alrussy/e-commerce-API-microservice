package com.alrussy_dev.order_service.cart_management.service;

import com.alrussy_dev.order_service.common.CartStatus;
import com.alrussy_dev.order_service.common.PagedResult;

interface BaseService<ID, DtoResponse, DtoRequest> {

    DtoResponse findCartById(ID id);

    DtoResponse findCartByCustomrerId(String customrerId);

    PagedResult<DtoResponse> findByStatus(CartStatus status, Integer pageNumper, Integer sizePage);

    PagedResult<DtoResponse> findAll(Integer pageNumper, Integer sizePage);
}
