package com.alrussy.product_service.service.impls.name_details_impl;

import com.alrussy.product_service.mapper.NameDetailsMapper;
import com.alrussy.product_service.mapper.impls.PageMapper;
import com.alrussy.product_service.model.dto.PagedResult;
import com.alrussy.product_service.model.dto.details_dto.NameDetailsRequest;
import com.alrussy.product_service.model.dto.details_dto.NameDetailsResponse;
import com.alrussy.product_service.model.entities.NamesDetails;
import com.alrussy.product_service.model.entities.ValueDetails;
import com.alrussy.product_service.model.entities.id.ValueDetailsId;
import com.alrussy.product_service.repository.NameDetailsRepository;
import com.alrussy.product_service.service.BaseNameDetailsService;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NameDetailsServiceImpl implements BaseNameDetailsService {

    private final NameDetailsMapper mapper;
    private final NameDetailsRepository repository;

    @Override
    public PagedResult<NameDetailsResponse> findAll(int pageNumber) {
        var page = PageRequest.of(pageNumber <= 1 ? 0 : pageNumber - 1, 20);
        return new PageMapper<NameDetailsResponse>()
                .toPageResponse(repository.findAll(page).map(name -> mapper.fromEntity(name)));
    }

    @Override
    public NameDetailsResponse findById(String id) {
        return mapper.fromEntity(repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString())));
    }

    @Override
    public List<NameDetailsResponse> findByIds(List<String> ids) {
        return repository.findAllById(ids).stream()
                .map(name -> mapper.fromEntity(name))
                .toList();
    }

    @Override
    public final String save(NameDetailsRequest request) {
        return repository.save(mapper.toEntity(request)).getName();
    }

    @Transactional
    @Override
    public final void delete(String id) {
        var details = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
        repository.delete(details);
    }

    @Override
    public final NameDetailsResponse update(String id, NameDetailsRequest request) throws Exception {
        return null;
    }

    @Override
    public final Long count() {
        return repository.count();
    }

    @Override
    public NameDetailsResponse addValue(NameDetailsRequest request) {
        NamesDetails name = repository
                .findById(request.name())
                .orElseThrow(() -> new EntityNotFoundException(request.name().toString()));
        List<ValueDetails> vs = new ArrayList<>();

        vs.add(ValueDetails.builder()
                .valueDetails(ValueDetailsId.builder()
                        .name(request.name())
                        .value(request.value())
                        .build())
                .nameDetails(name)
                .build());
        name.setValues(vs);
        return mapper.fromEntity(repository.save(name));
    }
}
