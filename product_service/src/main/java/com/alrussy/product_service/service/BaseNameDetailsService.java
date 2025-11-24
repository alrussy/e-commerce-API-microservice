package com.alrussy.product_service.service;

import com.alrussy.product_service.model.dto.details_dto.NameDetailsRequest;
import com.alrussy.product_service.model.dto.details_dto.NameDetailsResponse;
import java.util.List;

public interface BaseNameDetailsService extends BaseService<String, NameDetailsResponse, NameDetailsRequest> {

    List<NameDetailsResponse> findByIds(List<String> id);

    NameDetailsResponse addValue(NameDetailsRequest request);
}
