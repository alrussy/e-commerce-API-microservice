package com.alrussy_dev.order_service.queries.mapper;

public interface BaseMapper<Entity, DtoResponse, DtoRequest> {
    Entity toEntity(DtoRequest request);

    DtoResponse fromEntity(Entity entity);
}
