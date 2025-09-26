package com.smartfactory.erp.controller;

import com.smartfactory.erp.dto.InventoryDto;
import com.smartfactory.erp.dto.StockRequestDto;
import com.smartfactory.erp.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //컨트롤러
@RequestMapping("/api/inventory") //기본 URL지정
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public List<InventoryDto> getInventory(String inventoryId, Integer materialId) {

        // 4. 두 조건이 모두 있는 경우
        if (inventoryId != null && materialId != null) {
            return inventoryService.findByInventoryIdAndMaterialId(inventoryId, materialId);
        }
        // 2. 재고 ID만 있는 경우
        else if (inventoryId != null) {
            return inventoryService.findByInventoryId(inventoryId);
        }
        // 3. 자재 ID만 있는 경우
        else if (materialId != null) {
            return inventoryService.findByMaterialId(materialId);
        }
        // 1. 아무 조건도 없는 경우
        else {
            return inventoryService.findAll();
        }
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

}