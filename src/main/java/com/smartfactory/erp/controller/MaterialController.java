package com.smartfactory.erp.controller;

import com.smartfactory.erp.dto.CustomerDto;
import com.smartfactory.erp.dto.MaterialDto;
import com.smartfactory.erp.entity.MaterialEntity;
import com.smartfactory.erp.service.CustomerService;
import com.smartfactory.erp.service.MaterialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/materials")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    //조건 조회
    @GetMapping
    public List<MaterialDto> getMaterials(String materialNm, String category) {

        // 4. 자재명과 자재 분류 조건이 모두 있는 경우
        if (materialNm != null && !materialNm.isEmpty() && category != null && !category.isEmpty()) {
            return materialService.findByNameAndCategory(materialNm, category);
        }
        // 2. 자재명만 있는 경우
        else if (materialNm != null && !materialNm.isEmpty()) {
            return materialService.findByMaterialName(materialNm);
        }
        // 3. 자재 분류만 있는 경우
        else if (category != null && !category.isEmpty()) {
            return materialService.findByCategory(category);
        }
        // 1. 아무 조건도 없는 경우 (전체 조회)
        else {
            return materialService.findAll();
        }
    }

    // 단건 조회
    @GetMapping("/{materialId}")
    public MaterialDto getMaterial(@PathVariable("materialId") Integer material) {
        return materialService.getMaterial(material);
    }

    //저장
    @PostMapping("")
    public MaterialDto saveMaterial(@RequestBody MaterialDto material){
        return materialService.saveMaterial(material);
    }

    //모두저장
    @PostMapping("/saveAll")
    public List<MaterialDto> saveAll(@RequestBody List<MaterialDto> materials) {
        log.info("POST+++++++++++++++++{}", materials);
        return materialService.saveAllMaterials(materials);
        // JPA가 id==null → insert, id!=null → update 처리
    }

    // 삭제
    @DeleteMapping("/{materialId}")
    public void deleteMaterial(@PathVariable Integer materialId) {
        materialService.deleteMaterial(materialId);
    }
}
