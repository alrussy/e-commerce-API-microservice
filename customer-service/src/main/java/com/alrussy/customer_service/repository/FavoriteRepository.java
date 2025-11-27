package com.alrussy.customer_service.repository;

import com.alrussy.customer_service.entity.Favorite;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findByUsername(String username);

    @Transactional
    int deleteBySkuCodeAndUsername(String skuCode, String username);
}
