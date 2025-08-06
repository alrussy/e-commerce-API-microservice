package product_app.mapper;

public interface BaseMapper<Entity, DtoResponse, DtoRequest> {

    Entity toEntity(DtoRequest request);

    DtoResponse fromEntity(Entity entity);
}
