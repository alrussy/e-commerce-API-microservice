package product_app.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import product_app.model.entities.SkuProduct;

public interface SkuProductRepository extends JpaRepository<SkuProduct, String>, JpaSpecificationExecutor<SkuProduct> {
    /**
     * this method to check if skuProducts by productId is exists
     * @param productId
     * @return Boolean
     */
    Boolean existsByProductIdProductId(long productId);
    /**
     * this method to find skuProducts by productId
     * @param productId
     * @return List<SkuProduct>
     */
    @EntityGraph(attributePaths = {"details"})
    List<SkuProduct> findByProductIdProductId(long productId);
    /**
     * this method to find skuProducts by categoryId
     *
     * @param categoryId
     * @return List<SkuProduct>
     */
    @EntityGraph(attributePaths = {"details"})
    List<SkuProduct> findByProductIdCategoryId(long categoryId);

    /**
     * this method to find all skuProducts where isPrimary=true
     * @param pageable
     * @param isPrimary
     * @return Page<SkuProduct>
     */
    Page<SkuProduct> findAllByIsPrimary(Pageable pageable, boolean isPrimary);

    /**
     * this method to find first 5 skuProducts where department id= id and isPrimary=true
     * @param id
     * @param isPrimary
     * @return List<SkuProduct>
     */
    List<SkuProduct> findFirst5ByProductDepartmentDepartmentIdAndIsPrimary(Long departmentId, Boolean isPrimary);

    /**
     * this method to find first 3 skuProducts where brand id= id and isPrimary=true
     * @param id
     * @param isPrimary
     * @return List<SkuProduct>
     */
    List<SkuProduct> findFirst3ByProductBrandIdAndIsPrimary(Long id, boolean isPrimary);

    /**
     * this method to skuProducts where skuCode in list of skuCodes
     * @param skuCodes
     * @return List<SkuProduct>
     */
    List<SkuProduct> findBySkuCodeIn(List<String> skuCodes);
    /**
     * this method to find skuProduct by skuCode=id
     * @param skuCode
     * @return Optional<SkuProduct>
     */
    Optional<SkuProduct> findBySkuCode(String skuCode);

    /**
     * this method to find skuProducts by category id
     * @param id
     * @return List<SkuProduct>
     */
    //	@EntityGraph(attributePaths = {"details"})
    //	List<SkuProduct> findByCategoryDetailsNamesCategoryId(long id);

}
