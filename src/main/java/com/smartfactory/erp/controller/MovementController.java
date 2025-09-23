package com.smartfactory.erp.controller;

import com.smartfactory.erp.dto.MovementDto;
import com.smartfactory.erp.service.MovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movement")
@RequiredArgsConstructor
public class MovementController {

    private final MovementService movementService;

    // 자재품질검사 웹훅 (수정 없음)
    @PostMapping("/webhook")
    public ResponseEntity<Void> receiveFromMes(@RequestBody List<MovementDto> movementDtos){
        movementService.saveMovements(movementDtos);
        return ResponseEntity.noContent().build();
    }

    /**
     * 동적 조건으로 재고 이력 조회
     * 예: /api/movement?materialId=101
     */
    @GetMapping
    public ResponseEntity<List<MovementDto>> getMovements(
            @RequestParam(required = false) Integer movementId,
            @RequestParam(required = false) Integer materialId
    ) {
        List<MovementDto> movements = movementService.searchMovements(movementId, materialId);
        return ResponseEntity.ok(movements);
    }
}