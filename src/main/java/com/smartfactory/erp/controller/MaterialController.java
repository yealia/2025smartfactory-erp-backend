package com.smartfactory.erp.controller;

import com.smartfactory.erp.dto.MaterialDto; // DTO는 별도로 생성해야 합니다.
import com.smartfactory.erp.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materials") // ✅ 자재 관련 API의 기본 경로
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    /**
     * [조회] 자재 목록을 동적 조건으로 검색합니다.
     * 예: GET /api/materials?materialNm=볼트&category=부품
     */
    @GetMapping
    public ResponseEntity<List<MaterialDto>> searchMaterials(
            @RequestParam(required = false) String materialNm,
            @RequestParam(required = false) String category
    ) {
        List<MaterialDto> materials = materialService.searchMaterials(materialNm, category);
        return ResponseEntity.ok(materials);
    }

    /**
     * [조회] ID로 특정 자재의 상세 정보를 조회합니다.
     * 예: GET /api/materials/101
     */
    @GetMapping("/{id}")
    public ResponseEntity<MaterialDto> getMaterialById(@PathVariable Integer id) {
        MaterialDto material = materialService.getMaterialById(id);
        return ResponseEntity.ok(material);
    }

    /**
     * [생성] 신규 자재를 등록합니다.
     * 예: POST /api/materials
     */
    @PostMapping
    public ResponseEntity<MaterialDto> createMaterial(@RequestBody MaterialDto materialDto) {
        // ID는 DB에서 자동 생성되므로 DTO의 ID는 null로 설정
        materialDto.setMaterialId(null);
        MaterialDto createdMaterial = materialService.saveMaterial(materialDto);
        // 성공적인 생성(201 CREATED) 상태 코드와 함께 생성된 자원 반환
        return new ResponseEntity<>(createdMaterial, HttpStatus.CREATED);
    }

    /**
     * [수정] 기존 자재 정보를 수정합니다.
     * 예: PUT /api/materials/101
     */
    @PutMapping("/{id}")
    public ResponseEntity<MaterialDto> updateMaterial(@PathVariable Integer id, @RequestBody MaterialDto materialDto) {
        materialDto.setMaterialId(id); // URL 경로의 ID를 DTO에 명확히 설정
        MaterialDto updatedMaterial = materialService.saveMaterial(materialDto);
        return ResponseEntity.ok(updatedMaterial);
    }

    /**
     * [삭제] 특정 자재를 삭제합니다.
     * 예: DELETE /api/materials/101
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable Integer id) {
        materialService.deleteMaterial(id);
        // 성공적으로 삭제되었으며 반환할 콘텐츠가 없음을 의미 (204 No Content)
        return ResponseEntity.noContent().build();
    }
}

