package com.smartfactory.erp.controller;

import com.smartfactory.erp.dto.SupplierDto; // DTO는 별도로 생성해야 합니다.
import com.smartfactory.erp.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    /**
     * 동적 조건으로 거래처를 검색합니다.
     * 예: GET /api/suppliers?supplierName=삼성&contractDate=2025-09-23
     */
    @GetMapping
    public ResponseEntity<List<SupplierDto>> searchSuppliers(
            @RequestParam(required = false) String supplierName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate contractDate
    ) {
        List<SupplierDto> suppliers = supplierService.searchSuppliers(supplierName, contractDate);
        return ResponseEntity.ok(suppliers);
    }

    /**
     * ID로 특정 거래처의 상세 정보를 조회합니다.
     * 예: GET /api/suppliers/1
     */
    @GetMapping("/{id}")
    public ResponseEntity<SupplierDto> getSupplierById(@PathVariable Integer id) {
        SupplierDto supplier = supplierService.getSupplierById(id);
        return ResponseEntity.ok(supplier);
    }

    /**
     * 신규 거래처를 등록합니다.
     * 예: POST /api/suppliers
     */
    @PostMapping
    public ResponseEntity<SupplierDto> createSupplier(@RequestBody SupplierDto supplierDto) {
        // ID는 자동 생성되므로 null 또는 0으로 전달되어야 함
        supplierDto.setSupplierId(null);
        SupplierDto createdSupplier = supplierService.saveSupplier(supplierDto);
        return new ResponseEntity<>(createdSupplier, HttpStatus.CREATED);
    }

    /**
     * 기존 거래처 정보를 수정합니다.
     * 예: PUT /api/suppliers/1
     */
    @PutMapping("/{id}")
    public ResponseEntity<SupplierDto> updateSupplier(@PathVariable Integer id, @RequestBody SupplierDto supplierDto) {
        supplierDto.setSupplierId(id); // URL의 ID를 DTO에 설정하여 명확하게 함
        SupplierDto updatedSupplier = supplierService.saveSupplier(supplierDto);
        return ResponseEntity.ok(updatedSupplier);
    }

    /**
     * 여러 거래처를 한 번에 등록/수정합니다.
     * 예: POST /api/suppliers/bulk
     */
    @PostMapping("/bulk")
    public ResponseEntity<List<SupplierDto>> saveAllSuppliers(@RequestBody List<SupplierDto> supplierDtos) {
        List<SupplierDto> savedSuppliers = supplierService.saveAllSuppliers(supplierDtos);
        return ResponseEntity.ok(savedSuppliers);
    }

    /**
     * 특정 거래처를 삭제합니다.
     * 예: DELETE /api/suppliers/1
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Integer id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build(); // 성공적으로 삭제되었음을 의미 (204 No Content)
    }
}
