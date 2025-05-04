package product_app.repository;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import product_app.model.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {

    List<Category> findByNameLike(String name);

    @Transactional
    @Modifying
    @Query(value = "delete from brand_category c where c.category_id = ?1  ", nativeQuery = true)
    int deleteBrandCategory(Long categoryId);

    @Transactional
    @Modifying
    @Query(value = "delete from name_details_category cd where cd.category_id = ?1", nativeQuery = true)
    int deleteCategorydetailsName(Long categoryId);

    @Transactional
    @Modifying
    @Query(
            value = "INSERT INTO name_details_category (category_id, name_details_id) VALUES (?1, ?2)",
            nativeQuery = true)
    int saveWithNameDetails(Long categoryId, String detailsnameId);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO brand_category (category_id, brand_id) VALUES (?1, ?2)", nativeQuery = true)
    int saveWithBrands(Long categoryId, Long brandId);

    @EntityGraph(attributePaths = {"brandCategory.brand"})
    List<Category> findAll();

    List<Category> findByIsFeatureEquals(Boolean isFeature);
}
