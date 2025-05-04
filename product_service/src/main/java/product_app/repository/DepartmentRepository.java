package product_app.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import product_app.model.entities.Department;
import product_app.model.entities.DepartmentProjection;
import product_app.model.entities.id.DepartmentId;

public interface DepartmentRepository
        extends JpaRepository<Department, DepartmentId>, JpaSpecificationExecutor<Department> {

    Optional<Department> findByIdDepartmentId(Long id);

    Boolean existsByName(String name);

    @Query(
            value =
                    "SELECT d.department_id FROM departments as d join (select * from products as p where p.department_id=?1 limit 2)as pp on d.department_id=pp.department_id",
            nativeQuery = true)
    List<DepartmentProjection> findDepartmentWithFirst2Products(Long id);

    List<Department> findByIdCategoryId(Long id);

    @Transactional
    @Modifying
    void deleteByIdCategoryId(Long id);

    List<Department> findByName(String name);
}
