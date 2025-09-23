package com.smartfactory.erp.controller;

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

    /**
     * 🔍 발주 목록 동적 검색
     * GET /api/purchaseOrders
     */
    @GetMapping
    public ResponseEntity<List<PurchaseOrderDto>> searchOrders(
            // @RequestParam을 사용하여 URL 쿼리 파라미터를 받습니다.
            // required = false는 해당 파라미터가 없어도 오류를 발생시키지 않도록 합니다.
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) String supplierName,
            @RequestParam(required = false) Integer status
    ) {
        // ✅ 서비스 메소드의 시그니처와 동일하게 4개의 파라미터를 전달합니다.
        List<PurchaseOrderDto> orders = purchaseOrderService.searchPurchaseOrders(startDate, endDate, supplierName, status);
        return ResponseEntity.ok(orders);
    }

    /**
     * 📖 발주 단건 상세 조회
     * GET /api/purchaseOrders/{purchaseOrderId}
     */
    @GetMapping("/{purchaseOrderId}")
    public ResponseEntity<PurchaseOrderWithDetailsDto> getOrderById(@PathVariable String purchaseOrderId) {
        PurchaseOrderWithDetailsDto order = purchaseOrderService.getPurchaseOrderById(purchaseOrderId);
        return ResponseEntity.ok(order);
    }

    /**
     * 💾 발주 저장 (생성/수정)
     * POST /api/purchaseOrders
     */
    @PostMapping
    public ResponseEntity<PurchaseOrderWithDetailsDto> saveOrder(@RequestBody PurchaseOrderWithDetailsDto dto) {
        PurchaseOrderWithDetailsDto savedOrder = purchaseOrderService.savePurchaseOrderWithDetails(dto);
        return ResponseEntity.ok(savedOrder);
    }

    /**
     * 🗑️ 발주 삭제
     * DELETE /api/purchaseOrders/{purchaseOrderId}
     */
    @DeleteMapping("/{purchaseOrderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String purchaseOrderId) {
        purchaseOrderService.deletePurchaseOrder(purchaseOrderId);
        return ResponseEntity.noContent().build();
    }
}