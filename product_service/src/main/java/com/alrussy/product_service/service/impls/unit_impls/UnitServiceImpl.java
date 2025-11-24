package com.alrussy.product_service.service.impls.unit_impls;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.alrussy.product_service.mapper.UnitMapper;
import com.alrussy.product_service.mapper.impls.PageMapper;
import com.alrussy.product_service.model.dto.PagedResult;
import com.alrussy.product_service.model.dto.unit_dto.UnitRequest;
import com.alrussy.product_service.model.dto.unit_dto.UnitResponse;
import com.alrussy.product_service.repository.UnitRepository;
import com.alrussy.product_service.service.BaseUnitService;

@Primary
@Service
@RequiredArgsConstructor
public class UnitServiceImpl implements BaseUnitService {

    private final UnitMapper mapper;
    private final UnitRepository repository;

    @Override
    public PagedResult<UnitResponse> findAll(int pageNum) {
        var page = PageRequest.of(pageNum <= 1 ? 0 : pageNum - 1, 10);
        return new PageMapper<UnitResponse>()
                .toPageResponse(repository.findAll(page).map(unit -> mapper.fromEntity(unit)));
    }

    @Override
    public UnitResponse findById(Long id) {
        return mapper.fromEntity(repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString())));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Long save(UnitRequest request) {
        var entity = mapper.toEntity(request);
        return repository.save(entity).getId();
    }

    @Override
    public UnitResponse update(Long id, UnitRequest request) throws Exception {

        var entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
        if (request.name() != null) {
            entity.setName(request.name());
        }

        return mapper.fromEntity(repository.save(entity));
    }

    @Override
    public Long count() {
        return repository.count();
    }
}
