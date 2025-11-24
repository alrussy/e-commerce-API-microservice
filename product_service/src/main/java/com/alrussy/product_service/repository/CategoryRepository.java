package com.alrussy.product_service.repository;

import com.alrussy.product_service.model.entities.Category;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {

    List<Category> findByNameLike(String name);

    @Transactional
    @Modifying
    @Query(value = "delete from brand_category c where c.category_id = ?1  ", nativeQuery = true)
    int deleteBrandCategory(Long categoryId);

    @Transactional
    @Modifying
    @Query(value = "delete from departments d where d.category_id = ?1 and d.id=?2", nativeQuery = true)
    int deleteDepartment(Long categoryId, Long departmentId);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO departments (category_id,name ) VALUES (?1, ?2)", nativeQuery = true)
    int saveDepartments(Long categoryId, String name);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO brand_category (category_id, brand_id) VALUES (?1, ?2)", nativeQuery = true)
    int saveWithBrands(Long categoryId, Long brandId);

    @Transactional
    @Modifying
    @Query(
            value = "INSERT INTO names_details_category (category_id, detail_name_id) VALUES (?1, ?2)",
            nativeQuery = true)
    int saveWithNameDetails(Long categoryId, String nameDetails);

    @EntityGraph(attributePaths = {"brandCategory.brand"})
    List<Category> findAll();

    List<Category> findByGroupCategoryId(Long categoryId);

    @Transactional
    @Modifying
    @Query(
            value = "delete from names_details_category nc where nc.category_id = ?1 and nc.detail_name_id=?2",
            nativeQuery = true)
    int deleteNameDetails(Long categoryId, String name);

    List<Category> findByBrandCategoryBrandCategoryIdBrandId(Long brandId);
}
