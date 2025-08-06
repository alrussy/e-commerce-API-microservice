package product_app.service.impls.category_impls;

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
import product_app.mapper.CategoryMapper;
import product_app.mapper.DepartmentMapper;
import product_app.mapper.impls.PageMapper;
import product_app.model.dto.PagedResult;
import product_app.model.dto.category_dto.CategoryRequest;
import product_app.model.dto.category_dto.CategoryResponse;
import product_app.model.entities.Category;
import product_app.model.entities.Department;
import product_app.model.entities.NamesDetails;
import product_app.model.entities.id.NamesDetailsCategoryId;
import product_app.model.entities.table.NamesDetailsAndCategory;
import product_app.repository.CategoryRepository;
import product_app.service.BaseCategoryService;

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
    public List<CategoryResponse> findByName(String name) {
        return categoryRepository.findByNameLike("%" + name + "%").stream()
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
        categoryRepository.deleteById(id);
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
            categoryRepository.deleteDepartments(id);
            var departments = categoryRequest.departments().stream()
                    .map(dept -> departmentMapper.toEntity(categoryFind.getId(), dept))
                    .collect(Collectors.toList());
            categoryFind.addDepartemnt(departments);
        }

        return categoryMapper.fromEntity(categoryRepository.save(categoryFind));
    }

    @Override
    public Long count() {
        // TODO Auto-generated method stub
        return categoryRepository.count();
    }
}
