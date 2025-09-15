package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<InventoryEntity, String> {
    // 재고 id 큰거 확인
    Optional<InventoryEntity> findByMaterialIdAndWarehouseAndLocation(Integer materialId, String warehouse, String location);
    Optional<InventoryEntity> findTopByInventoryIdStartingWithOrderByInventoryIdDesc(String prefix);
}
