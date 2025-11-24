package com.alrussy_dev.order_service.checkout_management.service;

import com.alrussy_dev.order_service.checkout_management.entity.Checkout;
import com.alrussy_dev.order_service.common.CheckoutStatus;
import com.alrussy_dev.order_service.common.PagedResult;

interface BaseService<ID, DtoResponse, DtoRequest> {

    DtoResponse cancelCheckout(ID id);

    Checkout cancelPay(String orderId);

    DtoResponse findById(ID id);

    DtoResponse findByCartId(String cartId);

    PagedResult<DtoResponse> findByStatus(CheckoutStatus status, Integer pageNumper, Integer sizePage);

    PagedResult<DtoResponse> findAll(Integer pageNumper, Integer sizePage);

    Checkout findByIdAndCartId(String id, String cartId);
}
