package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.MovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementRepository extends JpaRepository<MovementEntity, Integer>,
        JpaSpecificationExecutor<MovementEntity> {
    // qcId를 기준으로 데이터가 존재하는지 여부(true/false)를 반환합니다.
    boolean existsByQcId(Integer qcId);
}