package com.smartfactory.erp.controller;

import com.smartfactory.erp.dto.BomDto;
import com.smartfactory.erp.service.BomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boms")
@RequiredArgsConstructor
public class BomController {

    private final BomService bomService;

    /**
     * BOM 생성 (Create)
     */
    @PostMapping
    public ResponseEntity<BomDto> createBom(@RequestBody BomDto bomDto) {
        BomDto createdBom = bomService.createBom(bomDto);
        return new ResponseEntity<>(createdBom, HttpStatus.CREATED);
    }

    /**
     * BOM 단일 조회 (Read by ID)
     */
    @GetMapping("/{id}")
    public ResponseEntity<BomDto> getBomById(@PathVariable Integer id) {
        BomDto bomDto = bomService.getBomById(id);
        return ResponseEntity.ok(bomDto);
    }

    /**
     * BOM 목록 조회 (Search)
     */
    @GetMapping
    public List<BomDto> getBoms(@RequestParam(required = false) String vesselId) {
        return bomService.searchBomsByVesselId(vesselId);
    }

    /**
     * BOM 수정 (Update)
     */
    @PutMapping("/{id}")
    public ResponseEntity<BomDto> updateBom(@PathVariable Integer id, @RequestBody BomDto bomDto) {
        BomDto updatedBom = bomService.updateBom(id, bomDto);
        return ResponseEntity.ok(updatedBom);
    }

    /**
     * BOM 삭제 (Delete)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBom(@PathVariable Integer id) {
        bomService.deleteBom(id);
        return ResponseEntity.noContent().build();
    }
}