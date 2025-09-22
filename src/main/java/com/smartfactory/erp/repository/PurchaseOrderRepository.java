package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.PurchaseOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrderEntity, String>,
        JpaSpecificationExecutor<PurchaseOrderEntity> {
}
