package product_app.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import product_app.model.dto.brand_dto.projections.BrandProjectionGlobal;
import product_app.model.entities.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    Optional<Brand> findByName(String name);

    @Query(
            value = "SELECT * FROM brand_category bc join brands b on (bc.brand_id = b.id) where bc.category_id = ?1",
            nativeQuery = true)
    List<Brand> findByCategory(Long id);

    @Query(
            value =
                    "select *,(select COUNT(*)  from  products as p where p.brand_id=b.id) as product_count from brands as b limit:page ",
            nativeQuery = true)
    List<BrandProjectionGlobal> findAllWithProductCount();

    @Query(
            value =
                    "select *,(select COUNT(*)  from  products as p where p.brand_id=b.id )as product_count from brands as b  where b.id=:id ",
            nativeQuery = true)
    Optional<BrandProjectionGlobal> findByIdWithProductCount(Long id);

    @Query(
            value =
                    "select *,(select COUNT(*)  from  products as p where p.brand_id=b.id )\r\n"
                            + "as product_count from brands  as b \r\n"
                            + " join \r\n"
                            + " (select m.name as category_name,m.brand_id as brand_id from (select * from categories as c left join brand_category as bc on c.id=352 ) as m   ) as n where n.brand_id= b.id",
            nativeQuery = true)
    List<BrandProjectionGlobal> findByCatregoryIdWithProductCount(Long id);

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

    @Query(
            value =
                    "select *,(select COUNT(*)  from  products as p where p.brand_id=b.id) as product_count from brands as b where b.is_feature =?1",
            nativeQuery = true)
    List<BrandProjectionGlobal> findByIsFeatureWithCount(boolean isfeature);
}
