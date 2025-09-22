package com.smartfactory.erp.service;

import com.smartfactory.erp.client.WebhookClient;
import com.smartfactory.erp.dto.InspectionRequestDto;
import com.smartfactory.erp.dto.PurchaseDetailDto;
import com.smartfactory.erp.dto.PurchaseOrderDto;
import com.smartfactory.erp.dto.PurchaseOrderWithDetailsDto;
import com.smartfactory.erp.entity.PurchaseDetailEntity;
import com.smartfactory.erp.entity.PurchaseOrderEntity;
import com.smartfactory.erp.repository.PurchaseDetailRepository;
import com.smartfactory.erp.repository.PurchaseOrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseDetailRepository purchaseDetailRepository;
    private final WebhookClient webhookClient;

    @Value("${partner.server.base-url}")
    private String mesBaseUrl;

    @Value("${partner.server.api.inspection}")
    private String inspectionApi;

    private String getInspectionUrl() {
        return mesBaseUrl + inspectionApi;
    }

    // 조회부
    public List<PurchaseOrderDto> searchOrders(String purchaseOrderId,
                                               Integer supplierId,
                                               Integer status,
                                               LocalDate startDate,
                                               LocalDate endDate) {
        Specification<PurchaseOrderEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (purchaseOrderId != null && !purchaseOrderId.isEmpty()) {
                predicates.add(cb.like(root.get("purchaseOrderId"), "%" + purchaseOrderId + "%"));
            }
            if (supplierId != null) {
                predicates.add(cb.equal(root.get("supplierId"), supplierId));
            }
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            if (startDate != null && endDate != null) {
                predicates.add(cb.between(root.get("orderDate"), startDate, endDate));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return purchaseOrderRepository.findAll(spec)
                .stream()
                .map(PurchaseOrderDto::fromEntity) // DTO 변환 매퍼 필요
                .toList();
    }


    // 발주 상세 조회
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<PurchaseDetailDto> getOrderDetails(String purchaseOrderId) {
        return purchaseDetailRepository.findByPurchaseOrder_PurchaseOrderId(purchaseOrderId)
                .stream()
                .map(PurchaseDetailDto::fromEntity)
                .toList();
    }

    // 저장
    @Transactional
    public PurchaseOrderDto savePurchaseOrder(PurchaseOrderDto dto) {
        PurchaseOrderEntity saved = purchaseOrderRepository.save(dto.toEntity());
        return PurchaseOrderDto.fromEntity(saved);
    }

    @Transactional
    public List<PurchaseDetailDto> saveOrderDetails(String purchaseOrderId, List<PurchaseDetailDto> detailDtos) {
        PurchaseOrderEntity order = purchaseOrderRepository.findById(purchaseOrderId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 발주서 ID: " + purchaseOrderId));

        // 기존 상세 삭제 후 재저장 (업데이트 전략)
        purchaseDetailRepository.deleteByPurchaseOrder_PurchaseOrderId(purchaseOrderId);

        List<PurchaseDetailEntity> entities = detailDtos.stream()
                .map(dto -> dto.toEntity(order)) // 연관관계 세팅
                .toList();

        List<PurchaseDetailEntity> saved = purchaseDetailRepository.saveAll(entities);
        return saved.stream().map(PurchaseDetailDto::fromEntity).toList();
    }

    // 마스터 + 상세 한번에 저장
    @Transactional
    public PurchaseOrderWithDetailsDto savePurchaseOrderWithDetails(PurchaseOrderWithDetailsDto payload) {
        // 1) 마스터 저장
        PurchaseOrderEntity order = purchaseOrderRepository.save(payload.getPurchaseOrder().toEntity());

        // 2) 기존 상세 삭제 후 재저장
        purchaseDetailRepository.deleteByPurchaseOrder_PurchaseOrderId(order.getPurchaseOrderId());

        List<PurchaseDetailEntity> details = payload.getOrderDetails().stream()
                .map(d -> d.toEntity(order))
                .toList();

        List<PurchaseDetailEntity> savedDetails = purchaseDetailRepository.saveAll(details);

        // 3) 결과 조립
        PurchaseOrderWithDetailsDto result = new PurchaseOrderWithDetailsDto();
        result.setPurchaseOrder(PurchaseOrderDto.fromEntity(order));
        result.setOrderDetails(savedDetails.stream().map(PurchaseDetailDto::fromEntity).toList());
        return result;
    }

    // 삭제
    @Transactional
    public void deletePurchaseOrder(String purchaseOrderId) {
        // FK 제약 고려: 상세 → 마스터 순으로 삭제
        purchaseDetailRepository.deleteByPurchaseOrder_PurchaseOrderId(purchaseOrderId);
        purchaseOrderRepository.deleteById(purchaseOrderId);
    }

    // MES 연동 (검사 요청)
    @Transactional
    public void requestInspections(String purchaseOrderId, List<InspectionRequestDto> inspectionRequests) {
        String url = getInspectionUrl();
        log.info("Webhook Target URL = {}", url);

        // 마스터 검증
        PurchaseOrderEntity order = purchaseOrderRepository.findById(purchaseOrderId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 발주 ID: " + purchaseOrderId));

        // 로그
//        inspectionRequests.forEach(req -> {
//            if (req.getDetails() != null) {
//                req.getDetails().forEach(d ->
//                        log.info("검사요청 상세: 발주={}, 주문상세ID={}, 자재ID={}",
//                                req.getPurchaseOrderId(), d.getOrderDetailId(), d.getMaterialId())
//                );
//            }
//        });

        // Webhook 전송 (배열 통째로 보냄)
        log.info("[ERP→MES] POST {} body={}", url, inspectionRequests);
        webhookClient.send(url, inspectionRequests);

        // 상태 업데이트
        order.setStatus(1);
        purchaseOrderRepository.save(order);
    }

}
