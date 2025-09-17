package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.MovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovementRepository extends JpaRepository<MovementEntity, Integer> {
    List<MovementEntity> findByMovementId(Integer movementId);
    List<MovementEntity> findByMaterialId(Integer materialId);
    List<MovementEntity> findByMovementIdAndMaterialId(Integer movementId, Integer materialId);
}
