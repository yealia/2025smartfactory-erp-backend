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
    //전체 조회
    @GetMapping
    public List<MovementDto> getAll() {
        return movementService.findAll();
    }


}
