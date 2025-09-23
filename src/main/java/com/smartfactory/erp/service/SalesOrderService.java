package com.smartfactory.erp.service;

import com.smartfactory.erp.entity.SalesOrderEntity;
import com.smartfactory.erp.repository.SalesOrderRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SalesOrderService {

    private final SalesOrderRepository salesOrderRepository;

    /**
     * 동적 검색을 통해 판매 주문 목록을 조회합니다.
     */
    public List<SalesOrderEntity> searchSalesOrders(String customerId, String vesselId) {
        Specification<SalesOrderEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (customerId != null && !customerId.trim().isEmpty()) {
                predicates.add(cb.like(root.get("customerId"), "%" + customerId + "%"));
            }
            if (vesselId != null && !vesselId.trim().isEmpty()) {
                predicates.add(cb.like(root.get("vesselId"), "%" + vesselId + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return salesOrderRepository.findAll(spec);
    }

    /**
     * ID를 통해 단일 판매 주문을 조회합니다.
     */
    public SalesOrderEntity getSalesOrderById(String id) {
        return salesOrderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sales order not found with ID: " + id));
    }

    /**
     * 신규 판매 주문을 생성합니다.
     */
    @Transactional
    public SalesOrderEntity createSalesOrder(SalesOrderEntity salesOrder) {
        // ID는 클라이언트에서 제공하지 않고 서비스에서 생성하는 경우, 이 곳에서 로직을 구현합니다.
        // 현재는 DTO가 없으므로 엔티티를 직접 사용합니다.
        // salesOrder.setOrderId(UUID.randomUUID().toString()); // 예시
        return salesOrderRepository.save(salesOrder);
    }

    /**
     * 기존 판매 주문을 수정합니다.
     */
    @Transactional
    public SalesOrderEntity updateSalesOrder(String id, SalesOrderEntity updatedSalesOrder) {
        SalesOrderEntity existingSalesOrder = salesOrderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sales order not found with ID: " + id));

        // 엔티티 필드 업데이트 로직
        existingSalesOrder.setCustomerId(updatedSalesOrder.getCustomerId());
        existingSalesOrder.setVesselId(updatedSalesOrder.getVesselId());
        existingSalesOrder.setOrderDate(updatedSalesOrder.getOrderDate());
        existingSalesOrder.setStatus(updatedSalesOrder.getStatus());
        existingSalesOrder.setRemark(updatedSalesOrder.getRemark());

        return salesOrderRepository.save(existingSalesOrder);
    }

    /**
     * 판매 주문을 삭제합니다.
     */
    @Transactional
    public void deleteSalesOrder(String id) {
        if (!salesOrderRepository.existsById(id)) {
            throw new IllegalArgumentException("Sales order not found with ID: " + id);
        }
        salesOrderRepository.deleteById(id);
    }
}