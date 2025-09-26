package com.smartfactory.erp.service;

import com.smartfactory.erp.dto.SalesOrderDto;
import com.smartfactory.erp.entity.CustomerEntity;
import com.smartfactory.erp.entity.SalesOrderEntity;
import com.smartfactory.erp.entity.VesselEntity;
import com.smartfactory.erp.repository.SalesOrderRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SalesOrderService {

    private final SalesOrderRepository salesOrderRepository;

    /**
     * 동적 검색
     */
    public List<SalesOrderDto> searchSalesOrders(String customerId, String vesselId) {
        Specification<SalesOrderEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (customerId != null && !customerId.trim().isEmpty()) {
                predicates.add(cb.like(root.get("customer").get("customerId"), "%" + customerId + "%"));
            }
            if (vesselId != null && !vesselId.trim().isEmpty()) {
                predicates.add(cb.like(root.get("vessel").get("vesselId"), "%" + vesselId + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return salesOrderRepository.findAll(spec).stream()
                .map(SalesOrderDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * ID로 단일 조회
     */
    public SalesOrderDto getSalesOrderById(String id) {
        return salesOrderRepository.findById(id)
                .map(SalesOrderDto::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("Sales order not found with ID: " + id));
    }

    /**
     * 신규 생성
     */
    @Transactional
    public SalesOrderDto createSalesOrder(SalesOrderDto salesOrderDto) {
        SalesOrderEntity salesOrder = salesOrderDto.toEntity();
        SalesOrderEntity savedEntity = salesOrderRepository.save(salesOrder);
        return SalesOrderDto.fromEntity(savedEntity);
    }

    /**
     * 기존 주문 수정
     */
    @Transactional
    public SalesOrderDto updateSalesOrder(String id, SalesOrderDto updatedSalesOrderDto) {
        SalesOrderEntity existingSalesOrder = salesOrderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sales order not found with ID: " + id));

        // 기본 필드 업데이트
        existingSalesOrder.setOrderDate(updatedSalesOrderDto.getOrderDate());
        existingSalesOrder.setCustomerPoNo(updatedSalesOrderDto.getCustomerPoNo());
        existingSalesOrder.setCurrencyCode(updatedSalesOrderDto.getCurrencyCode());
        existingSalesOrder.setStatus(updatedSalesOrderDto.getStatus());
        existingSalesOrder.setTotalAmount(updatedSalesOrderDto.getTotalAmount());
        existingSalesOrder.setCreatedBy(updatedSalesOrderDto.getCreatedBy());
        existingSalesOrder.setApprovedDate(updatedSalesOrderDto.getApprovedDate());
        existingSalesOrder.setApprovedBy(updatedSalesOrderDto.getApprovedBy());
        existingSalesOrder.setRemark(updatedSalesOrderDto.getRemark());

        // 연관관계 업데이트
        if (updatedSalesOrderDto.getCustomerId() != null) {
            CustomerEntity customer = new CustomerEntity();
            customer.setCustomerId(updatedSalesOrderDto.getCustomerId());
            existingSalesOrder.setCustomer(customer);
        }
        if (updatedSalesOrderDto.getVesselId() != null) {
            VesselEntity vessel = new VesselEntity();
            vessel.setVesselId(updatedSalesOrderDto.getVesselId());
            existingSalesOrder.setVessel(vessel);
        }

        SalesOrderEntity updatedEntity = salesOrderRepository.save(existingSalesOrder);
        return SalesOrderDto.fromEntity(updatedEntity);
    }

    /**
     * 삭제
     */
    @Transactional
    public void deleteSalesOrder(String id) {
        if (!salesOrderRepository.existsById(id)) {
            throw new IllegalArgumentException("Sales order not found with ID: " + id);
        }
        salesOrderRepository.deleteById(id);
    }
}
