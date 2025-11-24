package com.alrussy.product_service.repository;

import com.alrussy.product_service.model.dto.brand_dto.projections.BrandProjectionGlobal;
import com.alrussy.product_service.model.entities.Brand;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface BrandRepository extends JpaRepository<Brand, Long>, JpaSpecificationExecutor<Brand> {

    List<Brand> findByNameContaining(String name);

    @Query(
            value = "SELECT * FROM brand_category bc join brands b on (bc.brand_id = b.id) where bc.category_id = ?1",
            nativeQuery = true)
    List<Brand> findByCategory(Long id);

    @Query(
            value =
                    "select *,(select COUNT(*)  from  products as p where p.brand_id=b.id) as product_count from brands as b",
            nativeQuery = true)
    List<BrandProjectionGlobal> findAllWithProductCount();

    @Query(
            value =
                    "select *,(select COUNT(*)  from  products as p where p.brand_id=b.id )as product_count from brands as b  where b.id=:id ",
            nativeQuery = true)
    Optional<BrandProjectionGlobal> findByIdWithProductCount(Long id);

    @Query(
            value = "select distinct *,\r\n"
                    + "(select count(*)  from  product.products as p where p.brand_id=b.id )as product_count\r\n"
                    + " from  product.brands as b \r\n"
                    + " join (\r\n"
                    + " select m.name as category_name , m.brand_id  \r\n"
                    + " from \r\n"
                    + " (select * from product.categories as c \r\n"
                    + " left join product.brand_category as bc \r\n"
                    + " on c.id=bc.category_id\r\n"
                    + " where c.id=:id\r\n"
                    + " )as m  \r\n"
                    + " \r\n"
                    + " ) as n where n.brand_id = b.id limit :limit  \r\n",
            nativeQuery = true)
    List<BrandProjectionGlobal> findByCatregoryIdWithProductCount(Long id, Integer limit);

    @Query(
            value =
                    "select *,(select COUNT(*)  from  products as p where p.brand_id=b.id )as product_count from brands as b  where b.name=:name ",
            nativeQuery = true)
    Optional<BrandProjectionGlobal> findByNameWithCount(String name);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO brand_category (category_id, brand_id) VALUES (?1, ?2)", nativeQuery = true)
    int saveWithCategories(Long categoryId, Long brandId);

    @Transactional
    @Modifying
    @Query(value = "delete from brand_category b where b.brand_id = ?1  ", nativeQuery = true)
    int deleteBrandCategory(Long brandID);

    @Transactional
    @Modifying
    @Query(value = "delete from brand_category b where b.brand_id = ?1", nativeQuery = true)
    int deleteBrand(Long brandId);

    List<Brand> findByBrandCategoryBrandCategoryIdCategoryIdAndNameContaining(Long categoryId, String name);
}
