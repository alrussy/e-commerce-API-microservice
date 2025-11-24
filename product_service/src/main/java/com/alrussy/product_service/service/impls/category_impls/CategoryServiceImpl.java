package com.alrussy.product_service.service.impls.category_impls;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alrussy.product_service.mapper.CategoryMapper;
import com.alrussy.product_service.mapper.DepartmentMapper;
import com.alrussy.product_service.mapper.impls.PageMapper;
import com.alrussy.product_service.model.dto.PagedResult;
import com.alrussy.product_service.model.dto.category_dto.CategoryFilter;
import com.alrussy.product_service.model.dto.category_dto.CategoryRequest;
import com.alrussy.product_service.model.dto.category_dto.CategoryResponse;
import com.alrussy.product_service.model.dto.department_dto.DepartmentRequest;
import com.alrussy.product_service.model.entities.Category;
import com.alrussy.product_service.model.entities.Department;
import com.alrussy.product_service.model.entities.NamesDetails;
import com.alrussy.product_service.model.entities.id.NamesDetailsCategoryId;
import com.alrussy.product_service.model.entities.table.NamesDetailsAndCategory;
import com.alrussy.product_service.repository.CategoryRepository;
import com.alrussy.product_service.repository.specification.CategorySpecification;
import com.alrussy.product_service.service.BaseCategoryService;

@Primary
@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements BaseCategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    public PagedResult<CategoryResponse> findAll(int pageNumber) {
        var page = PageRequest.of(pageNumber <= 1 ? 0 : pageNumber - 1, 10);
        return new PageMapper<CategoryResponse>()
                .toPageResponse(categoryRepository.findAll(page).map(category -> categoryMapper.fromEntity(category)));
    }

    @Override
    public CategoryResponse findById(Long id) {
        return categoryMapper.fromEntity(
                categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString())));
    }

    @Override
    public List<CategoryResponse> findByGroupId(Long id) {
        return categoryRepository.findByGroupCategoryId(id).stream()
                .map((category -> categoryMapper.fromEntity(category)))
                .toList();
    }

    @Override
    public List<CategoryResponse> findByName(String name) {
        return categoryRepository.findByNameLike("%" + name + "%").stream()
                .map(categoryMapper::fromEntity)
                .toList();
    }

    @Override
    public List<CategoryResponse> filter(CategoryFilter filter) {
        var categorySpecification = new CategorySpecification(filter);
        return categoryRepository.findAll(categorySpecification).stream()
                .map(categoryMapper::fromEntity)
                .toList();
    }

    @Override
    @Transactional
    public Long save(CategoryRequest request) {
        log.info("start save category");
        Category categorySave = categoryRepository.save(categoryMapper.toEntity(request));

        if (request.departments() != null) {
            List<Department> departments = request.departments().stream()
                    .map(dept -> departmentMapper.toEntity(categorySave.getId(), dept))
                    .collect(Collectors.toList());
            categorySave.setDepartments(departments);
        }

        if (request.namesDetails() != null) {
            Set<NamesDetailsAndCategory> namesDetails = request.namesDetails().stream()
                    .map(namedetails -> NamesDetailsAndCategory.builder()
                            .id(NamesDetailsCategoryId.builder()
                                    .categoryId(categorySave.getId())
                                    .detailNameId(namedetails)
                                    .build())
                            .category(
                                    Category.builder().id(categorySave.getId()).build())
                            .nameDetails(
                                    NamesDetails.builder().name(namedetails).build())
                            .build())
                    .collect(Collectors.toSet());
            categorySave.setNameDetailsCategory(namesDetails);
        }

        log.info("category :{}", categorySave.toString());

        return categoryRepository.save(categorySave).getId();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        var category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));

        categoryRepository.delete(category);
    }

    @Override
    public Integer deleteDepartment(Long categoryId, Long departmentId) {
        int result = categoryRepository.deleteDepartment(categoryId, departmentId);
        return result;
    }

    @Override
    public Integer deleteNameDetails(Long categoryId, String name) {
        int result = categoryRepository.deleteNameDetails(categoryId, name);
        return result;
    }

    @Override
    @Transactional
    public CategoryResponse update(Long id, CategoryRequest categoryRequest) {
        Category categoryFind =
                categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
        if (categoryRequest.name() != null) {
            categoryFind.setName(categoryRequest.name());
        }
        if (categoryRequest.imageUrl() != null) {
            categoryFind.setImageUrl(categoryRequest.imageUrl());
        }
        if (categoryRequest.departments() != null) {
            var d1 =
                    categoryFind.getDepartments().stream().map(t -> t.getName()).toList();
            categoryRequest.departments().stream().map(t -> t.name()).forEach(x -> {
                if (!d1.contains(x)) {
                    categoryFind.addDepartemnt(
                            departmentMapper.toEntity(categoryFind.getId(), new DepartmentRequest(x)));
                }
            });
            if (categoryRequest.namesDetails() != null) {
                var nd = categoryFind.getNameDetailsCategory().stream()
                        .map((n -> n.getNameDetails().getName()))
                        .toList();

                Set<NamesDetailsAndCategory> namesDetails = categoryRequest.namesDetails().stream()
                        .filter(ndr -> !nd.contains(ndr))
                        .map(namedetails -> NamesDetailsAndCategory.builder()
                                .id(NamesDetailsCategoryId.builder()
                                        .categoryId(id)
                                        .detailNameId(namedetails)
                                        .build())
                                .category(Category.builder()
                                        .id(categoryFind.getId())
                                        .build())
                                .nameDetails(
                                        NamesDetails.builder().name(namedetails).build())
                                .build())
                        .collect(Collectors.toSet());
                categoryFind.addNameDetailst(namesDetails);
            }
        }
        log.info(categoryFind.toString());
        return categoryMapper.fromEntity(categoryRepository.save(categoryFind));
    }

    @Override
    public Long count() {
        // TODO Auto-generated method stub
        return categoryRepository.count();
    }

    @Override
    public List<CategoryResponse> findByBrandId(Long brandId) {
        return categoryRepository.findByBrandCategoryBrandCategoryIdBrandId(brandId).stream()
                .map((category -> categoryMapper.fromEntity(category)))
                .toList();
    }
}
