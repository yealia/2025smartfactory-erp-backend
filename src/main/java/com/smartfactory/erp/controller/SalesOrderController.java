package com.smartfactory.erp.controller;

import com.smartfactory.erp.entity.SalesOrderEntity;
import com.smartfactory.erp.service.SalesOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales_orders")
@RequiredArgsConstructor
public class SalesOrderController {

    private final SalesOrderService salesOrderService;

    /**
     * GET /api/sales-orders?customerId=...&vesselId=...
     * 동적 조건으로 판매 주문 목록을 조회합니다.
     */
    @GetMapping
    public ResponseEntity<List<SalesOrderEntity>> searchSalesOrders(
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) String vesselId
    ) {
        List<SalesOrderEntity> salesOrders = salesOrderService.searchSalesOrders(customerId, vesselId);
        return ResponseEntity.ok(salesOrders);
    }

    /**
     * GET /api/sales_orders/{id}
     * ID로 특정 판매 주문을 조회합니다.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SalesOrderEntity> getSalesOrderById(@PathVariable String id) {
        SalesOrderEntity salesOrder = salesOrderService.getSalesOrderById(id);
        return ResponseEntity.ok(salesOrder);
    }

    /**
     * POST /api/sales_orders
     * 신규 판매 주문을 등록합니다.
     */
    @PostMapping
    public ResponseEntity<SalesOrderEntity> createSalesOrder(@RequestBody SalesOrderEntity salesOrder) {
        SalesOrderEntity createdSalesOrder = salesOrderService.createSalesOrder(salesOrder);
        return new ResponseEntity<>(createdSalesOrder, HttpStatus.CREATED);
    }

    /**
     * PUT /api/sales_orders/{id}
     * 기존 판매 주문을 수정합니다.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SalesOrderEntity> updateSalesOrder(@PathVariable String id, @RequestBody SalesOrderEntity salesOrder) {
        SalesOrderEntity updatedSalesOrder = salesOrderService.updateSalesOrder(id, salesOrder);
        return ResponseEntity.ok(updatedSalesOrder);
    }

    /**
     * DELETE /api/sales_orders/{id}
     * 특정 판매 주문을 삭제합니다.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalesOrder(@PathVariable String id) {
        salesOrderService.deleteSalesOrder(id);
        return ResponseEntity.noContent().build();
    }
}