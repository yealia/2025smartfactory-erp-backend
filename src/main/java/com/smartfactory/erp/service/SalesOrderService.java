package com.smartfactory.erp.service;

import com.smartfactory.erp.dto.SalesOrderDto; // DTO import 추가
import com.smartfactory.erp.entity.SalesOrderEntity;
import com.smartfactory.erp.repository.SalesOrderRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors; // Collectors import 추가

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SalesOrderService {

    private final SalesOrderRepository salesOrderRepository;

    /**
     * 동적 검색 (반환 타입을 List<SalesOrderDto>로 변경)
     */
    public List<SalesOrderDto> searchSalesOrders(String customerId, String vesselId) {
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
        // Entity 리스트를 DTO 리스트로 변환하여 반환
        return salesOrderRepository.findAll(spec).stream()
                .map(SalesOrderDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * ID로 단일 조회 (반환 타입을 SalesOrderDto로 변경)
     */
    public SalesOrderDto getSalesOrderById(String id) {
        return salesOrderRepository.findById(id)
                .map(SalesOrderDto::fromEntity) // Entity를 DTO로 변환
                .orElseThrow(() -> new IllegalArgumentException("Sales order not found with ID: " + id));
    }

    /**
     * 신규 생성 (파라미터를 DTO로 받고, DTO로 반환)
     */
    @Transactional
    public SalesOrderDto createSalesOrder(SalesOrderDto salesOrderDto) {
        SalesOrderEntity salesOrder = salesOrderDto.toEntity();
        SalesOrderEntity savedEntity = salesOrderRepository.save(salesOrder);
        return SalesOrderDto.fromEntity(savedEntity); // 저장된 Entity를 DTO로 변환하여 반환
    }

    /**
     * 기존 주문 수정 (파라미터를 DTO로 받고, DTO로 반환)
     */
    @Transactional
    public SalesOrderDto updateSalesOrder(String id, SalesOrderDto updatedSalesOrderDto) {
        SalesOrderEntity existingSalesOrder = salesOrderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sales order not found with ID: " + id));

        // DTO의 정보로 엔티티 필드 업데이트
        existingSalesOrder.setCustomerId(updatedSalesOrderDto.getCustomerId());
        existingSalesOrder.setVesselId(updatedSalesOrderDto.getVesselId());
        existingSalesOrder.setOrderDate(updatedSalesOrderDto.getOrderDate());
        existingSalesOrder.setStatus(updatedSalesOrderDto.getStatus());
        existingSalesOrder.setRemark(updatedSalesOrderDto.getRemark());
        // 필요한 다른 필드들도 여기에 추가...

        SalesOrderEntity updatedEntity = salesOrderRepository.save(existingSalesOrder);
        return SalesOrderDto.fromEntity(updatedEntity); // 수정된 Entity를 DTO로 변환하여 반환
    }

    /**
     * 판매 주문 삭제
     */
    @Transactional
    public void deleteSalesOrder(String id) {
        if (!salesOrderRepository.existsById(id)) {
            throw new IllegalArgumentException("Sales order not found with ID: " + id);
        }
        salesOrderRepository.deleteById(id);
    }
}