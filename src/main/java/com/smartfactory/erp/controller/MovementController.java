package com.smartfactory.erp.controller;

import com.smartfactory.erp.dto.MovementDto;
import com.smartfactory.erp.service.MovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movements") // ✅ URL을 복수형으로 변경 (RESTful 컨벤션)
@RequiredArgsConstructor
public class MovementController {

    private final MovementService movementService;

    // [기존] 자재품질검사 웹훅
    @PostMapping("/webhook")
    public ResponseEntity<Void> receiveFromMes(@RequestBody List<MovementDto> movementDtos){
        movementService.saveMovementsFromWebhook(movementDtos);
        return ResponseEntity.noContent().build();
    }

    /**
     * 동적 조건으로 재고 이력 조회
     * 예: GET /api/movements?materialId=101
     */
    @GetMapping
    public ResponseEntity<List<MovementDto>> getMovements(
            @RequestParam(required = false) Integer movementId,
            @RequestParam(required = false) Integer materialId
    ) {
        List<MovementDto> movements = movementService.searchMovements(movementId, materialId);
        return ResponseEntity.ok(movements);
    }

    /**
     * ✅ [추가] 신규 재고 이력 등록
     * 예: POST /api/movements
     */
    @PostMapping
    public ResponseEntity<MovementDto> createMovement(@RequestBody MovementDto movementDto) {
        // ID는 자동 생성이므로 null로 설정
        movementDto.setMovementId(null);
        MovementDto createdMovement = movementService.saveMovement(movementDto);
        return new ResponseEntity<>(createdMovement, HttpStatus.CREATED);
    }

    /**
     * ✅ [추가] 기존 재고 이력 수정
     * 예: PUT /api/movements/1
     */
    @PutMapping("/{id}")
    public ResponseEntity<MovementDto> updateMovement(@PathVariable Integer id, @RequestBody MovementDto movementDto) {
        movementDto.setMovementId(id); // URL의 ID를 DTO에 설정
        MovementDto updatedMovement = movementService.saveMovement(movementDto);
        return ResponseEntity.ok(updatedMovement);
    }

    /**
     * ✅ [추가] 특정 재고 이력 삭제
     * 예: DELETE /api/movements/1
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovement(@PathVariable Integer id) {
        movementService.deleteMovement(id);
        return ResponseEntity.noContent().build();
    }
}