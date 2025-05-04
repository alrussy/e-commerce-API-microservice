package product_app.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import product_app.model.entities.Product;
import product_app.model.entities.id.ProductId;

public interface ProductRepository extends JpaRepository<Product, ProductId>, JpaSpecificationExecutor<Product> {

    Optional<Product> findByIdProductId(long id);

    Boolean existsByName(String name);

    Boolean existsByBrandId(Long brandId);

    Boolean existsByDepartmentIdDepartmentId(Long departmentId);

    @EntityGraph(attributePaths = {"brand", "brandCategory.brand", "department.category"})
    List<Product> findAll();

    @Transactional
    @Modifying
    int deleteByIdProductId(long id);

    List<Product> findByBrandId(Long brandId);

    int countByBrandId(Long id);

    List<Product> findFirst5ByDepartmentIdDepartmentId(Long departmentIds);

    List<Product> findByDepartmentIdDepartmentIdIn(List<Long> departmentIds);
}
