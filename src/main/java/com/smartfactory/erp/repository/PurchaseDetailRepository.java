package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.PurchaseDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseDetailRepository extends JpaRepository<PurchaseDetailEntity, Integer>,
        JpaSpecificationExecutor<PurchaseDetailEntity> {
}

