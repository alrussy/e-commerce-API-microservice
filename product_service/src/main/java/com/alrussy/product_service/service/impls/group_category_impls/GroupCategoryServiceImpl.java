package com.alrussy.product_service.service.impls.group_category_impls;

import com.alrussy.product_service.mapper.GroupCategoryMapper;
import com.alrussy.product_service.mapper.impls.PageMapper;
import com.alrussy.product_service.model.dto.PagedResult;
import com.alrussy.product_service.model.dto.group_category_dto.GroupCategoryRequest;
import com.alrussy.product_service.model.dto.group_category_dto.GroupCategoryResponse;
import com.alrussy.product_service.model.dto.group_category_dto.GroupFilter;
import com.alrussy.product_service.repository.GroupCategoryRepository;
import com.alrussy.product_service.repository.specification.GroupSpecification;
import com.alrussy.product_service.service.BaseGroupCategoryService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Primary
@Service
@RequiredArgsConstructor
public class GroupCategoryServiceImpl implements BaseGroupCategoryService {

    private final GroupCategoryMapper mapper;
    private final GroupCategoryRepository repository;

    @Override
    public PagedResult<GroupCategoryResponse> findAll(int pageNum) {
        var page = PageRequest.of(pageNum <= 1 ? 0 : pageNum - 1, 10);
        return new PageMapper<GroupCategoryResponse>()
                .toPageResponse(repository.findAll(page).map(category -> mapper.fromEntity(category)));
    }

    @Override
    public GroupCategoryResponse findById(Long id) {
        return mapper.fromEntity(repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString())));
    }

    @Override
    public List<GroupCategoryResponse> filter(GroupFilter filter) {
        var groupSpecification = new GroupSpecification(filter);
        return repository.findAll(groupSpecification).stream()
                .map(mapper::fromEntity)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Long save(GroupCategoryRequest request) {
        var entity = mapper.toEntity(request);
        return repository.save(entity).getId();
    }

    @Override
    public GroupCategoryResponse update(Long id, GroupCategoryRequest request) throws Exception {

        var entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
        if (request.name() != null) {
            entity.setName(request.name());
        }
        if (request.imageUrl() != null) {
            entity.setImageUrl(request.imageUrl());
        }
        return mapper.fromEntity(repository.save(entity));
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public List<GroupCategoryResponse> findByName(String name) {
        // TODO Auto-generated method stub
        return repository.findByNameLike("%" + name + "%").stream()
                .map(group -> mapper.fromEntity(group))
                .toList();
    }
}
