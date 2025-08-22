package com.smartfactory.erp.controller;

import com.smartfactory.erp.entity.Material;
import com.smartfactory.erp.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materials")
@RequiredArgsConstructor
public class MaterialController {
    private final MaterialService materialService;

    // 등록
    @PostMapping
    public Material createMaterial(@RequestBody Material material) {
        return materialService.saveMaterial(material);
    }

    // 전체 조회
    @GetMapping
    public List<Material> getAllMaterials() {
        return materialService.getAllMaterials();
    }

    // 단건 조회
    @GetMapping("/{id}")
    public Material getMaterial(@PathVariable Integer id) {
        return materialService.getMaterialById(id);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public void deleteMaterial(@PathVariable Integer id) {
        materialService.deleteMaterial(id);
    }

}
