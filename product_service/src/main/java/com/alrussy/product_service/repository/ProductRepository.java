package com.alrussy.product_service.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.alrussy.product_service.model.entities.Product;
import com.alrussy.product_service.model.entities.id.ProductId;

public interface ProductRepository extends JpaRepository<Product, ProductId>, JpaSpecificationExecutor<Product> {

    Optional<Product> findByIdProductId(long id);

    List<Product> findByIdCategoryId(long id);

    List<Product> findByBrandId(Long brandId);

    List<Product> findByIdCategoryIdAndNameContaining(long id, String name);

    List<Product> findByBrandIdAndNameContaining(long id, String name);

    Boolean existsByName(String name);

    Boolean existsByBrandId(Long brandId);

    Boolean existsByDepartmentDepartmentId(Long departmentId);

    @EntityGraph(attributePaths = {"brand", "brandCategory.brand", "department.category"})
    List<Product> findAll();

    @Transactional
    @Modifying
    int deleteByIdProductId(long id);

    int countByBrandId(Long id);

    List<Product> findFirst5ByDepartmentName(String departmentIds);

    List<Product> findByDepartmentNameIn(List<String> departmentIds);

    @Transactional
    @Modifying
    @Query(value = "delete from value_details_product d where d.product_id = ?1", nativeQuery = true)
    int deleteDetails(Long product);

    List<Product> findByNameContaining(String name);

    List<Product> findBySkuCodeStartsWith(String skucode);
}
