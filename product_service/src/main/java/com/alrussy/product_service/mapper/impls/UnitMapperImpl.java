package com.alrussy.product_service.mapper.impls;

import com.alrussy.product_service.mapper.UnitMapper;
import com.alrussy.product_service.model.dto.unit_dto.UnitRequest;
import com.alrussy.product_service.model.dto.unit_dto.UnitResponse;
import com.alrussy.product_service.model.entities.Unit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UnitMapperImpl implements UnitMapper {

    @Override
    public Unit toEntity(UnitRequest request) {

        return Unit.builder().name(request.name()).build();
    }

    @Override
    public UnitResponse fromEntity(Unit entity) {
        return new UnitResponse(entity.getId(), entity.getName());
    }
}
