package com.alrussy_dev.inventory_service.repository;

import com.alrussy_dev.inventory_service.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, String> {}
