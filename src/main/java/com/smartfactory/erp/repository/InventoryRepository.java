package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.InventoryEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, String>, JpaSpecificationExecutor<InventoryEntity> {

    /**
     * ✅ 비관적 쓰기 잠금을 적용하여 특정 자재 + 창고 + 위치를 조회합니다.
     *    동시성 문제를 방지하기 위해 서비스단 @Transactional 안에서 호출해야 합니다.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<InventoryEntity> findByMaterialIdAndWarehouseAndLocation(Integer materialId, String warehouse, String location);

    // ID prefix로 가장 최근(큰) InventoryId 찾기
    Optional<InventoryEntity> findTopByInventoryIdStartingWithOrderByInventoryIdDesc(String prefix);

    // 재고 ID 검색
    @EntityGraph(attributePaths = "material")
    List<InventoryEntity> findByInventoryIdContaining(String inventoryId);

    // 자재 ID로 전체 재고 검색
    @EntityGraph(attributePaths = "material")
    List<InventoryEntity> findByMaterialId(Integer materialId);

    // 재고 ID + 자재 ID 함께 검색
    @EntityGraph(attributePaths = "material")
    List<InventoryEntity> findByInventoryIdContainingAndMaterialId(String inventoryId, Integer materialId);

    // MES 연동용: 자재 ID로 해당 재고 하나 가져오기 (창고/위치 무시, 첫 번째만)
    Optional<InventoryEntity> findFirstByMaterialId(Integer materialId);

    @EntityGraph(attributePaths = "material")
    List<InventoryEntity> findAll();   // material까지 한 번에 조회
}

