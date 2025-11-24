package com.alrussy.product_service.mapper;

import com.alrussy.product_service.model.dto.details_dto.NameDetailsRequest;
import com.alrussy.product_service.model.dto.details_dto.NameDetailsResponse;
import com.alrussy.product_service.model.entities.NamesDetails;

public interface NameDetailsMapper extends BaseMapper<NamesDetails, NameDetailsResponse, NameDetailsRequest> {}
