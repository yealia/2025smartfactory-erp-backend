package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.InventoryEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<InventoryEntity, String> {
    // 특정 자재 + 창고 + 위치 조회 (재고 유무 확인)
    Optional<InventoryEntity> findByMaterialIdAndWarehouseAndLocation(Integer materialId, String warehouse, String location);

    // ID prefix로 가장 최근(큰) InventoryId 찾기
    Optional<InventoryEntity> findTopByInventoryIdStartingWithOrderByInventoryIdDesc(String prefix);

    // 재고 ID 검색
    @EntityGraph(attributePaths = "material")
    List<InventoryEntity> findByInventoryIdContaining(String inventoryId);

    // 자재 ID로 전체 재고 검색
    @EntityGraph(attributePaths = "material")
    List<InventoryEntity> findByMaterialId(Integer materialId);

    @EntityGraph(attributePaths = "material")
    // 재고 ID + 자재 ID 함께 검색
    List<InventoryEntity> findByInventoryIdContainingAndMaterialId(String inventoryId, Integer materialId);

    // MES 연동용: 자재 ID로 해당 재고 하나 가져오기
    // (창고/위치 무시하고 우선 첫 번째 기록만 가져옴)
    Optional<InventoryEntity> findFirstByMaterialId(Integer materialId);

    @EntityGraph(attributePaths = "material")
    List<InventoryEntity> findAll();   // material까지 한 번에 조회
}
