package com.smartfactory.erp.controller;

import com.smartfactory.erp.dto.MovementDto;
import com.smartfactory.erp.service.MovementService;
import com.smartfactory.erp.service.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movement")
@RequiredArgsConstructor
public class MovementController {

    private final MovementService movementService;

    //자재품질검사 웹훅
    @PostMapping("/webhook")
    public ResponseEntity<Void> receiveFromMes(@RequestBody List<MovementDto> movementDtos){
        movementService.saveMovements(movementDtos);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<MovementDto> getMovements(Integer movementId, Integer materialId) {

        // 4. 두 조건이 모두 있는 경우
        if (movementId != null && materialId != null) {
            return movementService.findByMovementIdAndMaterialId(movementId, materialId);
        }
        // 2. 이력 ID만 있는 경우
        else if (movementId != null) {
            return movementService.findByMovementId(movementId);
        }
        // 3. 자재 ID만 있는 경우
        else if (materialId != null) {
            return movementService.findByMaterialId(materialId);
        }
        // 1. 아무 조건도 없는 경우
        else {
            return movementService.findAll();
        }
    }
}


