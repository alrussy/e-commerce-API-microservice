package com.alrussy.customer_service.repository;

import com.alrussy.customer_service.entity.Address;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressesRepository extends JpaRepository<Address, Long> {

    List<Address> findByUsername(String username);

    int deleteByIdAndUsername(Long id, String username);
    //
    //	@Query(value=" delete from customer_favorite f where f.sku_code=?1", nativeQuery=true )
    //	int removeFavorite(String skuCode);

}
