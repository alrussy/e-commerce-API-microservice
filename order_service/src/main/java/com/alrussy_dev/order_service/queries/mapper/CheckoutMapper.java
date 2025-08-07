package com.alrussy_dev.order_service.queries.mapper;

import com.alrussy_dev.order_service.queries.model.Checkout;
import com.alrussy_dev.order_service.queries.model.dto.CheckoutRequest;
import com.alrussy_dev.order_service.queries.model.dto.CheckoutResponse;
import com.alrussy_dev.order_service.queries.model.dto.LineProductResponse;
import java.util.List;

public interface CheckoutMapper extends BaseMapper<Checkout, CheckoutResponse, CheckoutRequest> {

    CheckoutResponse fromEntity(Checkout entity, List<LineProductResponse> lineProduct);
}
