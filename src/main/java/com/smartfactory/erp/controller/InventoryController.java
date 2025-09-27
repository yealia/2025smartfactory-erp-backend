package com.smartfactory.erp.controller;

import com.smartfactory.erp.dto.InventoryDto;
import com.smartfactory.erp.dto.SimpleStockRequestDto;
import com.smartfactory.erp.dto.StockRequestDto;
import com.smartfactory.erp.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //컨트롤러
@RequestMapping("/api/inventory") //기본 URL지정
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    /**
     * ✅ [수정] 재고 목록을 동적 조건으로 검색합니다.
     * 예: GET /api/inventory?materialId=101
     */
    @GetMapping
    public ResponseEntity<List<InventoryDto>> searchInventories(
            @RequestParam(required = false) String inventoryId,
            @RequestParam(required = false) Integer materialId
    ) {
        List<InventoryDto> inventories = inventoryService.searchInventories(inventoryId, materialId);
        return ResponseEntity.ok(inventories);
    }

    /**
     * ✅ [추가] ID로 특정 재고 정보를 조회합니다.
     * 예: GET /api/inventory/IN20250923001
     */
    @GetMapping("/{id}")
    public ResponseEntity<InventoryDto> getInventoryById(@PathVariable String id) {
        InventoryDto inventory = inventoryService.getInventoryById(id);
        return ResponseEntity.ok(inventory);
    }

    @PostMapping
    public ResponseEntity<InventoryDto> createInventory(@RequestBody InventoryDto inventoryDto) {
        // inventoryId는 DTO에 포함되지 않도록 클라이언트에서 보냄
        InventoryDto createdInventory = inventoryService.createInventory(inventoryDto);
        return new ResponseEntity<>(createdInventory, HttpStatus.CREATED);
    }

    /**
     * 기존 재고 정보를 수정합니다.
     * 예: PUT /api/inventory/IN20250923001
     */
    @PutMapping("/{id}")
    public ResponseEntity<InventoryDto> updateInventory(@PathVariable String id, @RequestBody InventoryDto inventoryDto) {
        // URL의 ID를 서비스에 전달
        InventoryDto updatedInventory = inventoryService.updateInventory(id, inventoryDto);
        return ResponseEntity.ok(updatedInventory);
    }

    /**
     * ✅ [추가] 특정 재고를 삭제합니다.
     * 예: DELETE /api/inventory/IN20250923001
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable String id) {
        inventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }


    // ================== MES ↔ ERP 연동 ==================
    // StockRequest DTO를 받아서 창고/위치/자재ID 기반으로 정확히 처리

    /** (운영) 재고 차감 */
    @PostMapping("/deduct")
    public ResponseEntity<Void> deductStock(@RequestBody StockRequestDto request) {
        inventoryService.deductStock(request);
        return ResponseEntity.ok().build();
    }

    /** (운영) 재고 복구 */
    @PostMapping("/restore")
    public ResponseEntity<Void> restoreStock(@RequestBody StockRequestDto request) {
        inventoryService.restoreStock(request);
        return ResponseEntity.ok().build();
    }

//    /** (운영) 재고 수정 */
//    @PutMapping("/update")
//    public ResponseEntity<Void> updateStock(@RequestBody StockRequestDto request) {
//        inventoryService.updateStock(request);
//        return ResponseEntity.ok().build();
//    }

    @PostMapping("/restore-by-material")
    public ResponseEntity<Void> restoreStockByMaterialId(@RequestBody SimpleStockRequestDto request) {
        inventoryService.restoreStockByMaterialId(request);
        return ResponseEntity.ok().build();
    }

}