package com.smartfactory.erp.controller;

import com.smartfactory.erp.dto.SalesOrderDto; // DTO import
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

    // 모든 반환 타입을 SalesOrderDto로 수정
    @GetMapping
    public ResponseEntity<List<SalesOrderDto>> searchSalesOrders(
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) String vesselId
    ) {
        List<SalesOrderDto> salesOrders = salesOrderService.searchSalesOrders(customerId, vesselId);
        return ResponseEntity.ok(salesOrders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesOrderDto> getSalesOrderById(@PathVariable String id) {
        SalesOrderDto salesOrder = salesOrderService.getSalesOrderById(id);
        return ResponseEntity.ok(salesOrder);
    }

    // @RequestBody도 SalesOrderDto로 수정
    @PostMapping
    public ResponseEntity<SalesOrderDto> createSalesOrder(@RequestBody SalesOrderDto salesOrder) {
        SalesOrderDto createdSalesOrder = salesOrderService.createSalesOrder(salesOrder);
        return new ResponseEntity<>(createdSalesOrder, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalesOrderDto> updateSalesOrder(@PathVariable String id, @RequestBody SalesOrderDto salesOrder) {
        SalesOrderDto updatedSalesOrder = salesOrderService.updateSalesOrder(id, salesOrder);
        return ResponseEntity.ok(updatedSalesOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalesOrder(@PathVariable String id) {
        salesOrderService.deleteSalesOrder(id);
        return ResponseEntity.noContent().build();
    }
}