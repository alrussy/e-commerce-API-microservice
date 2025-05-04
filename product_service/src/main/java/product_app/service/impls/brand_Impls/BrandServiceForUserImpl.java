package product_app.service.impls.brand_Impls;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.MappedSuperclass;
import java.util.List;
import org.springframework.stereotype.Service;
import product_app.model.dto.brand_dto.BrandResponse;
import product_app.repository.BrandRepository;
import product_app.service.impls.product_Impls.ProductServiceForAdmin;

@Service
@MappedSuperclass
public class BrandServiceForUserImpl extends BrandServiceForAdmin {

    public BrandServiceForUserImpl(BrandRepository brandRepository, ProductServiceForAdmin productService) {
        super(brandRepository);
        this.brandRepository = brandRepository;
    }

    private final BrandRepository brandRepository;

    public List<BrandResponse> findAll() {

        return brandRepository.findAllWithProductCount().stream()
                .map(brand -> brand.mapToBrndResponse())
                .toList();
    }

    public BrandResponse findById(Long id) {
        return brandRepository
                .findByIdWithProductCount(id)
                .orElseThrow(() -> new EntityNotFoundException(id.toString()))
                .mapToBrndResponse();
    }

    @Override
    public List<BrandResponse> findByCategory(Long id) {

        return brandRepository.findByCatregoryIdWithProductCount(id).stream()
                .map(brand -> brand.mapToBrndResponse())
                .toList();
    }

    @Override
    public List<BrandResponse> findByIsFeature() {
        return brandRepository.findByIsFeatureWithCount(true).stream()
                .map(brand -> brand.mapToBrndResponse())
                .toList();
    }

    @Override
    public BrandResponse findByName(String name) {

        return brandRepository
                .findByNameWithCount(name)
                .orElseThrow(() -> new EntityNotFoundException(name.toString()))
                .mapToBrndResponse();
    }
}
