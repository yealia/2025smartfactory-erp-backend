package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.PurchaseDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseDetailRepository
        extends JpaRepository<PurchaseDetailEntity, Integer> {

    List<PurchaseDetailEntity> findByPurchaseOrder_PurchaseOrderId(String purchaseOrderId);

    void deleteByPurchaseOrder_PurchaseOrderId(String purchaseOrderId);

    boolean existsByPurchaseOrder_PurchaseOrderId(String purchaseOrderId);

    long countByPurchaseOrder_PurchaseOrderId(String purchaseOrderId);
}

