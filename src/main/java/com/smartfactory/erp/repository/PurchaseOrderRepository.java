
package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.PurchaseOrderEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrderEntity, String>,
        JpaSpecificationExecutor<PurchaseOrderEntity> {

    static Specification<PurchaseOrderEntity> containsPurchaseOrderId(String purchaseOrderId) {
        return (root, query, criteriaBuilder) ->
                !StringUtils.hasText(purchaseOrderId) ? null : criteriaBuilder.like(root.get("purchaseOrderId"), "%" + purchaseOrderId + "%");
    }


    static Specification<PurchaseOrderEntity> betweenOrderDate(LocalDate startDate, LocalDate endDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate != null && endDate != null) {
                return criteriaBuilder.between(root.get("orderDate"), startDate, endDate);
            }
            if (startDate != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("orderDate"), startDate);
            }
            if (endDate != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("orderDate"), endDate);
            }
            return null;
        };
    }

    static Specification<PurchaseOrderEntity> containsSupplierName(String supplierName) {
        return (root, query, criteriaBuilder) ->
                !StringUtils.hasText(supplierName) ? null : criteriaBuilder.like(root.get("supplier").get("supplierName"), "%" + supplierName + "%");
    }

    static Specification<PurchaseOrderEntity> hasStatus(Integer status) {
        return (root, query, criteriaBuilder) ->
                status == null ? null : criteriaBuilder.equal(root.get("status"), status);
    }
}
