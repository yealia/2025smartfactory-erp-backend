package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.PurchaseOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrderEntity, String> {

    // 발주번호로 검색
    List<PurchaseOrderEntity> findByPurchaseOrderIdContaining(String purchaseOrderId);

    // 공급업체로 검색
    List<PurchaseOrderEntity> findBySupplierId(Integer supplierId);

    // 상태로 검색
    List<PurchaseOrderEntity> findByStatus(Integer status);

    // 날짜 범위로 검색
    List<PurchaseOrderEntity> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);

    // 발주번호 + 공급업체
    List<PurchaseOrderEntity> findByPurchaseOrderIdContainingAndSupplierId(String purchaseOrderId, Integer supplierId);

    // 공급업체 + 상태
    List<PurchaseOrderEntity> findBySupplierIdAndStatus(Integer supplierId, Integer status);

    // 발주번호 + 날짜 범위
    List<PurchaseOrderEntity> findByPurchaseOrderIdContainingAndOrderDateBetween(
            String purchaseOrderId, LocalDate startDate, LocalDate endDate);

    // 공급업체 + 날짜 범위
    List<PurchaseOrderEntity> findBySupplierIdAndOrderDateBetween(
            Integer supplierId, LocalDate startDate, LocalDate endDate);

    // 발주번호 + 공급업체 + 상태 + 날짜범위 (풀옵션)
    List<PurchaseOrderEntity> findByPurchaseOrderIdContainingAndSupplierIdAndStatusAndOrderDateBetween(
            String purchaseOrderId, Integer supplierId, Integer status, LocalDate startDate, LocalDate endDate
    );
}
