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
    @Query(value = "delete from departments d where d.category_id = ?1", nativeQuery = true)
    int deleteDepartments(Long categoryId);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO departments (category_id,name ) VALUES (?1, ?2)", nativeQuery = true)
    int saveDepartments(Long categoryId, String name);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO brand_category (category_id, brand_id) VALUES (?1, ?2)", nativeQuery = true)
    int saveWithBrands(Long categoryId, Long brandId);

    @EntityGraph(attributePaths = {"brandCategory.brand"})
    List<Category> findAll();
}
