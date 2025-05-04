package product_app.service.impls.category_impls;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import product_app.mapper.CategoryMapper;
import product_app.mapper.DepartmentMapper;
import product_app.mapper.PageMapper;
import product_app.model.dto.PagedResult;
import product_app.model.dto.category_dto.CategoryRequest;
import product_app.model.dto.category_dto.CategoryResponse;
import product_app.model.entities.Category;
import product_app.repository.CategoryRepository;
import product_app.service.BaseCategoryService;
import product_app.service.impls.departmen_impls.DepartmentServiceImpl;

@Primary
@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceForAdmin implements BaseCategoryService {

    private final CategoryRepository categoryRepository;
    private final DepartmentServiceImpl departmentService;

    @Override
    public PagedResult<CategoryResponse> findAll(int pageNumber) {
        var page = PageRequest.of(pageNumber, 10);

        return new PageMapper<CategoryResponse>()
                .toPageResponse(categoryRepository.findAll(page).map(brand -> CategoryMapper.fromEntity(brand)));
    }

    @Override
    public CategoryResponse findById(Long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id.toString()))
                .mapToCategoryResponseWithDetailsNameAndBrandAndDepartment();
    }

    @Override
    public List<CategoryResponse> findByIsFeature() {

        return categoryRepository.findByIsFeatureEquals(true).stream()
                .map(Category::mapToCategoryResponseWithDetailsNameAndBrandAndDepartment)
                .toList();
    }

    @Override
    public List<CategoryResponse> findByName(String name) {

        return categoryRepository.findByNameLike("%" + name + "%").stream()
                .map(Category::mapToCategoryResponseWithDetailsNameAndBrandAndDepartment)
                .toList();
    }

    @Transactional
    @Override
    public CategoryResponse save(CategoryRequest category) {
        Category categorySave = categoryRepository.saveAndFlush(CategoryMapper.toEntity(category));

        category.departments().stream().forEach(request -> {
            categorySave.addDepartemnt(DepartmentMapper.toEntity(categorySave.getId(), request));
        });

        categorySave.getDepartments().stream().forEach(request -> {
            log.info(request.getName());
        });

        return categoryRepository.save(categorySave).mapToCategoryResponseOutDetailsNameAndBrand();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        categoryRepository.deleteBrandCategory(id);
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
        if (categoryRequest.isFeature() != null) {
            categoryFind.setIsFeature(categoryRequest.isFeature());
        }
        if (categoryRequest.imageUrl() != null) {
            categoryFind.setImageUrl(categoryRequest.imageUrl());
        }

        if (categoryRequest.departments() != null) {
            categoryFind.getDepartments().stream()
                    .forEach(department -> departmentService.deleteAll(
                            List.of(department.getId().getDepartmentId())));
            categoryFind.removeDepartemnt();
            categoryRequest.departments().stream().forEach(request -> {
                categoryFind.addDepartemnt(DepartmentMapper.toEntity(categoryFind.getId(), request));
            });
        }
        return categoryRepository.save(categoryFind).mapToCategoryResponseWithDetailsNameAndBrandAndDepartment();
    }

    @Override
    public Long count() {
        // TODO Auto-generated method stub
        return categoryRepository.count();
    }
}
