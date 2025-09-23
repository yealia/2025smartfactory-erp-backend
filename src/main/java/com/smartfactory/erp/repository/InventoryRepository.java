package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.InventoryEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, String>, JpaSpecificationExecutor<InventoryEntity> {

    /**
     * ✅ [수정] 비관적 쓰기 잠금을 적용하여 ID 조회 시 동시성 문제를 해결합니다.
     * 이 메소드가 실행되는 동안 다른 트랜잭션은 이 쿼리가 조회하는 데이터에 접근할 수 없습니다.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<InventoryEntity> findTopByInventoryIdStartingWithOrderByInventoryIdDesc(String prefix);

    // 재고 수량 변경 로직에 필요하므로 이 메소드도 유지합니다.
    Optional<InventoryEntity> findByMaterialIdAndWarehouseAndLocation(Integer materialId, String warehouse, String location);
}

