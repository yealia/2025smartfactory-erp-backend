package com.smartfactory.erp.controller;

import com.smartfactory.erp.dto.InspectionRequestDto;
import com.smartfactory.erp.dto.PurchaseDetailDto;
import com.smartfactory.erp.dto.PurchaseOrderDto;
import com.smartfactory.erp.dto.PurchaseOrderWithDetailsDto;
import com.smartfactory.erp.service.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/purchaseOrders")
@RequiredArgsConstructor
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    // 발주 마스터 조건 조회
    @GetMapping
    public List<PurchaseOrderDto> getPurchaseOrders(
            @RequestParam(required = false) String purchaseOrderId,
            @RequestParam(required = false) Integer supplierId,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {
        if (purchaseOrderId != null && supplierId != null && status != null && startDate != null && endDate != null) {
            return purchaseOrderService.getByAllConditions(purchaseOrderId, supplierId, status, startDate, endDate);
        } else if (purchaseOrderId != null) {
            return purchaseOrderService.getByPurchaseOrderId(purchaseOrderId);
        } else if (supplierId != null) {
            return purchaseOrderService.getBySupplier(supplierId);
        } else if (status != null) {
            return purchaseOrderService.getByStatus(status);
        } else if (startDate != null && endDate != null) {
            return purchaseOrderService.getByDateRange(startDate, endDate);
        } else {
            // 조건 없으면 전체 조회
            return purchaseOrderService.getAllSearch();
        }
    }

    // 발주 상세 조회 (마스터 선택 시)
    @GetMapping("/{purchaseOrderId}/details")
    public List<PurchaseDetailDto> getOrderDetails(@PathVariable String purchaseOrderId) {
        return purchaseOrderService.getOrderDetails(purchaseOrderId);
    }

    // 발주 저장 (마스터 + 상세)
    @PostMapping
    public PurchaseOrderWithDetailsDto savePurchaseOrder(@RequestBody PurchaseOrderWithDetailsDto purchaseOrderDto) {
        return purchaseOrderService.savePurchaseOrderWithDetails(purchaseOrderDto);
    }

    // 발주 상세 저장
    @PostMapping("/{purchaseOrderId}/details")
    public List<PurchaseDetailDto> saveOrderDetails(
            @PathVariable String purchaseOrderId,
            @RequestBody List<PurchaseDetailDto> details
    ) {
        return purchaseOrderService.saveOrderDetails(purchaseOrderId, details);
    }

    //마스터 + 상세 한번에 저장
    @PostMapping("/withDetails")
    public PurchaseOrderWithDetailsDto savePurchaseOrderWithDetails(
            @RequestBody PurchaseOrderWithDetailsDto dto
    ) {
        return purchaseOrderService.savePurchaseOrderWithDetails(dto);
    }

    // 발주 삭제
    @DeleteMapping("/{purchaseOrderId}")
    public void deletePurchaseOrder(@PathVariable String purchaseOrderId) {
        purchaseOrderService.deletePurchaseOrder(purchaseOrderId);
    }

    @PostMapping("/{purchaseOrderId}/inspectionRequests")
    public ResponseEntity<Void> requestInspections(
            @PathVariable String purchaseOrderId,
            @RequestBody List<InspectionRequestDto> inspectionRequests
    ) {
        inspectionRequests.forEach(req -> req.setPurchaseOrderId(purchaseOrderId));
        purchaseOrderService.requestInspections(purchaseOrderId, inspectionRequests);
        return ResponseEntity.ok().build();
    }
}
