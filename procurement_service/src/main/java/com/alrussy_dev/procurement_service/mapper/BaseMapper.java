package com.alrussy_dev.procurement_service.mapper;

public interface BaseMapper<Entity, DtoResponse, DtoRequest> {

    Entity toEntity(DtoRequest request);

    DtoResponse fromEntity(Entity entity);
}
