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
    public List<MaterialDto> getMaterials(String materialNm, LocalDate contractDate){
        //고객명 , 등록날짜 둘다 있는 경우
        if (materialNm != null && contractDate != null) {
            return materialService.getAllSearchMaterialContractDate(materialNm, contractDate);
        } else if (materialNm != null) {
            return materialService.getAllSearchMaterial(materialNm);
        } else if (contractDate != null) {
            return materialService.getAllSearchContractDate(contractDate);
        } else {
            return materialService.getAllSearch();
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
