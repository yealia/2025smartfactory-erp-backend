package com.smartfactory.erp.service;


import com.smartfactory.erp.dto.PurchaseOrderDto;
import com.smartfactory.erp.dto.PurchaseOrderWithDetailsDto;
import com.smartfactory.erp.entity.MaterialEntity;
import com.smartfactory.erp.entity.PurchaseDetailEntity;
import com.smartfactory.erp.entity.PurchaseOrderEntity;
import com.smartfactory.erp.entity.SupplierEntity;
import com.smartfactory.erp.repository.MaterialRepository;
import com.smartfactory.erp.repository.PurchaseDetailRepository;
import com.smartfactory.erp.repository.PurchaseOrderRepository;
import com.smartfactory.erp.repository.SupplierRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseDetailRepository purchaseDetailRepository;
    private final SupplierRepository supplierRepository;
    private final MaterialRepository materialRepository;

    /**
     * 🔍 동적 검색 (발주 날짜 범위, 공급사 ID)
     * - 발주 목록 조회 시 사용되며, 상세 정보(Details)는 포함하지 않습니다.
     */
    public List<PurchaseOrderDto> searchPurchaseOrders(LocalDate startDate, LocalDate endDate, String supplierName, Integer status) {

        // 1. 아무 조건도 없는 '빈' Specification으로 시작합니다.
        Specification<PurchaseOrderEntity> spec = (root, query, cb) -> cb.conjunction();

        // 2. 각 파라미터가 존재할 경우에만, .and()를 사용하여 조건을 추가합니다.
        if (startDate != null || endDate != null) {
            spec = spec.and(PurchaseOrderRepository.betweenOrderDate(startDate, endDate));
        }
        if (supplierName != null && !supplierName.trim().isEmpty()) {
            spec = spec.and(PurchaseOrderRepository.containsSupplierName(supplierName));
        }
        if (status != null) {
            spec = spec.and(PurchaseOrderRepository.hasStatus(status));
        }

        // 3. 최종적으로 조합된 Specification으로 데이터를 조회합니다.
        return purchaseOrderRepository.findAll(spec).stream()
                .map(PurchaseOrderDto::fromEntity)
                .toList();
    }

    // =========================
    // ✅ CRUD 기능
    // =========================

    /**
     * 단건 조회 (발주 + 상세 항목 포함)
     */
    public PurchaseOrderWithDetailsDto getPurchaseOrderById(String purchaseOrderId) {
        return purchaseOrderRepository.findById(purchaseOrderId)
                .map(PurchaseOrderWithDetailsDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("발주 정보를 찾을 수 없습니다. ID: " + purchaseOrderId));
    }

    /**
     * 저장 (등록 & 수정)
     * - 발주 정보와 상세 항목 리스트를 한번에 처리합니다.
     */
    @Transactional
    public PurchaseOrderWithDetailsDto savePurchaseOrderWithDetails(PurchaseOrderWithDetailsDto dto) {
        // 1. DTO에서 PurchaseOrder 정보 추출 및 엔티티 변환
        PurchaseOrderEntity orderEntity = dto.getPurchaseOrder().toEntity();

        // 2. 연관된 Supplier 엔티티를 조회하여 관계 설정
        SupplierEntity supplier = supplierRepository.findById(dto.getPurchaseOrder().getSupplierId())
                .orElseThrow(() -> new EntityNotFoundException("공급사 정보를 찾을 수 없습니다. ID: " + dto.getPurchaseOrder().getSupplierId()));
        orderEntity.setSupplier(supplier);

        // 3. (수정의 경우) 기존 상세 항목들 삭제
        if (orderEntity.getPurchaseOrderId() != null && purchaseOrderRepository.existsById(orderEntity.getPurchaseOrderId())) {
            PurchaseOrderEntity existingOrder = purchaseOrderRepository.findById(orderEntity.getPurchaseOrderId()).get();
            purchaseDetailRepository.deleteAll(existingOrder.getPurchaseOrderDetails());
        }

        // 4. 새로운 상세 항목 DTO 리스트를 엔티티 리스트로 변환
        List<PurchaseDetailEntity> detailEntities = dto.getOrderDetails().stream()
                .map(detailDto -> {
                    PurchaseDetailEntity detailEntity = detailDto.toEntity();
                    // 연관된 Material 엔티티 조회
                    MaterialEntity material = materialRepository.findById(detailDto.getMaterialId())
                            .orElseThrow(() -> new EntityNotFoundException("자재 정보를 찾을 수 없습니다. ID: " + detailDto.getMaterialId()));

                    // 양방향 연관관계 설정 (PurchaseDetail -> PurchaseOrder, Material)
                    detailEntity.setPurchaseOrder(orderEntity);
                    detailEntity.setMaterial(material);
                    return detailEntity;
                })
                .collect(Collectors.toList());

        // 양방향 연관관계 설정 (PurchaseOrder -> PurchaseDetails)
        orderEntity.setPurchaseOrderDetails(detailEntities);

        // 5. 엔티티 저장 (Cascade 설정에 따라 details도 함께 저장됨)
        PurchaseOrderEntity savedEntity = purchaseOrderRepository.save(orderEntity);

        // 6. 저장된 엔티티를 다시 DTO로 변환하여 반환
        return PurchaseOrderWithDetailsDto.fromEntity(savedEntity);
    }

    /**
     * 삭제
     * - 발주 정보 삭제 시, 연관된 상세 항목들도 함께 삭제됩니다. (CascadeType.ALL 또는 orphanRemoval=true 필요)
     */
    @Transactional
    public void deletePurchaseOrder(String purchaseOrderId) {
        if (!purchaseOrderRepository.existsById(purchaseOrderId)) {
            throw new EntityNotFoundException("삭제할 발주 정보가 없습니다. ID: " + purchaseOrderId);
        }
        purchaseOrderRepository.deleteById(purchaseOrderId);
    }
}