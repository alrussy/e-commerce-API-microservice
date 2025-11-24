package com.alrussy_dev.inventory_service.repository;

import com.alrussy_dev.inventory_service.model.ActionInventory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionInventoryRepository extends JpaRepository<ActionInventory, String> {

    Optional<ActionInventory> findByActionId(String actionId);

    @Override
    @EntityGraph(attributePaths = "lineProducts")
    List<ActionInventory> findAll();

    List<ActionInventory> findByIsProcessedAndIsPublished(boolean isProcessed, boolean isPublished, Sort descending);

    List<ActionInventory> findByLineProductsIdSkuCode(String skuCode);

    List<ActionInventory> findByLineProductsIdSkuCodeIn(List<String> skuCodes);
}
