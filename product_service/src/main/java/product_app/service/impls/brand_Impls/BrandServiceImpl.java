package product_app.service.impls.brand_Impls;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import product_app.mapper.BrandMapper;
import product_app.mapper.impls.PageMapper;
import product_app.model.dto.PagedResult;
import product_app.model.dto.brand_dto.BrandRequest;
import product_app.model.dto.brand_dto.BrandResponse;
import product_app.model.entities.Brand;
import product_app.model.entities.Category;
import product_app.model.entities.id.BrandCategoryId;
import product_app.model.entities.table.BrandCategory;
import product_app.repository.BrandRepository;
import product_app.service.BaseBrandService;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BaseBrandService {

    private final BrandMapper brandMapper;
    private final BrandRepository brandRepository;

    @Override
    public PagedResult<BrandResponse> findAll(int pageNumber) {
        var page = PageRequest.of(pageNumber <= 1 ? 0 : pageNumber - 1, 10);
        return new PageMapper<BrandResponse>()
                .toPageResponse(brandRepository.findAll(page).map(brand -> brandMapper.fromEntityOutCategory(brand)));
    }

    @Override
    public List<BrandResponse> findAllWithProductCount() {
        return brandRepository.findAllWithProductCount().stream()
                .map(brand -> brand.mapToBrandResponse())
                .toList();
    }

    @Override
    public BrandResponse findByIdWithProductCount(Long id) {
        return brandRepository
                .findByIdWithProductCount(id)
                .orElseThrow(() -> new EntityNotFoundException(id.toString()))
                .mapToBrandResponse();
    }

    @Override
    public List<BrandResponse> findByCategoryWithProductCount(Long id) {
        return brandRepository.findByCatregoryIdWithProductCount(id).stream()
                .map(brand -> brand.mapToBrandResponse())
                .toList();
    }

    @Override
    public BrandResponse findById(Long id) {
        return brandMapper.fromEntity(
                brandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString())));
    }

    @Override
    public List<BrandResponse> findByCategory(Long id) {

        return brandRepository.findByCategory(id).stream()
                .map(brandMapper::fromEntity)
                .toList();
    }

    @Override
    public BrandResponse findByName(String name) {
        return brandMapper.fromEntity(
                brandRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException(name.toString())));
    }

    @Override
    public final Long save(BrandRequest brand) {
        final Brand brandSave = brandRepository.saveAndFlush(brandMapper.toEntity(brand));
        brand.categoryIds().stream()
                .forEach(id -> brandSave.addCategory(BrandCategory.builder()
                        .brandCategoryId(BrandCategoryId.builder()
                                .categoryId(id)
                                .brandId(brandSave.getId())
                                .build())
                        .category(Category.builder().id(id).build())
                        .build()));
        return brandRepository.save(brandSave).getId();
    }

    @Transactional
    @Override
    public final void delete(Long id) {
        brandRepository.deleteBrandCategory(id);
        brandRepository.deleteById(id);
    }

    @Override
    public final BrandResponse update(Long id, BrandRequest brand) throws Exception {
        Brand brandFind = null;
        brandFind = brandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
        if (brand.name() != null) {
            brandFind.setName(brand.name());
        }
        if (brand.imageUrl() != null) brandFind.setImageUrl(brand.imageUrl());

        if (brand.categoryIds() != null) {
            brandRepository.deleteBrandCategory(id);
            brand.categoryIds().stream().forEach(t -> brandRepository.saveWithCategories(t, id));
        }

        return brandMapper.fromEntity(brandRepository.save(brandFind));
    }

    @Override
    public final Long count() {
        return brandRepository.count();
    }

    public final BrandResponse addImage(Long id, String url) {
        Brand brandFind = brandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
        brandFind.setImageUrl(url);
        return brandMapper.fromEntity(brandRepository.save(brandFind));
    }
}
