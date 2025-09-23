package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.MovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementRepository extends JpaRepository<MovementEntity, Integer>, JpaSpecificationExecutor<MovementEntity> {
}