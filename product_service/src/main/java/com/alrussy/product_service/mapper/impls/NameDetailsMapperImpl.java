package com.alrussy.product_service.mapper.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.alrussy.product_service.mapper.NameDetailsMapper;
import com.alrussy.product_service.model.dto.details_dto.NameDetailsRequest;
import com.alrussy.product_service.model.dto.details_dto.NameDetailsResponse;
import com.alrussy.product_service.model.entities.NamesDetails;

@Component
@RequiredArgsConstructor
public class NameDetailsMapperImpl implements NameDetailsMapper {

    @Override
    public NameDetailsResponse fromEntity(NamesDetails entity) {
        return new NameDetailsResponse(
                entity.getName(),
                entity.getValues() != null
                        ? entity.getValues().stream()
                                .map((value) -> value.getValueDetails().getValue())
                                .toList()
                        : null);
    }

    @Override
    public NamesDetails toEntity(NameDetailsRequest request) {
        // TODO Auto-generated method stub
        return NamesDetails.builder().name(request.name()).build();
    }
}
