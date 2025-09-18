package com.smartfactory.erp.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.smartfactory.erp.dto.MaterialDto;
import com.smartfactory.erp.entity.MaterialEntity;
import com.smartfactory.erp.entity.MaterialEntity;
import com.smartfactory.erp.entity.SupplierEntity;
import com.smartfactory.erp.repository.MaterialRepository;
import com.smartfactory.erp.repository.MaterialRepository;
import com.smartfactory.erp.repository.SupplierRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service //서비스
@RequiredArgsConstructor // final 필드 생성자 자동 생성 (의존성 주입)
public class MaterialService {

    private final MaterialRepository materialRepository;//불변
    private final SupplierRepository supplierRepository;//불변

    // 1. 조건이 둘 다 없을 경우 (전체 조회)
    public List<MaterialDto> findAll() {
        return materialRepository.findAll()
                .stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }

    // 2. 자재명만 있을 경우
    public List<MaterialDto> findByMaterialName(String materialNm) {
        return materialRepository.findByMaterialNmContaining(materialNm)
                .stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }

    // 3. 자재 분류만 있을 경우
    public List<MaterialDto> findByCategory(String category) {
        return materialRepository.findByCategoryContaining(category)
                .stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }

    // 4. 두 조건이 모두 있을 경우
    public List<MaterialDto> findByNameAndCategory(String materialNm, String category) {
        log.info("Service called with: materialNm={}, category={}", materialNm, category);
        return materialRepository.findByMaterialNmContainingAndCategoryContaining(materialNm, category)
                .stream()
                .map(MaterialDto::fromEntity)
                .toList();
    }

    //상세 조회
    public MaterialDto getMaterial(Integer materialId) {
        return materialRepository.findById(materialId)
                .map(MaterialDto::fromEntity)
                .orElse(null);
    }
    // =========================
    // 등록 / 수정
    // =========================
    @Transactional
    public MaterialDto saveMaterial(MaterialDto material){
        //1. 디티오 받음
        //2. 엔티티로 바꿔라
        //3. 레파에 보내라
        //4. 디티오로 바꿔라
        log.info("POST {} ", material);
        SupplierEntity supplierEntity = supplierRepository.findById(material.getSupplierId())
                .orElse(null);
        MaterialEntity materialEntity = materialRepository.save(material.toEntity(supplierEntity));
        return MaterialDto.fromEntity(materialEntity);
    }
    //여러 건 저장
    @Transactional
    public List<MaterialDto> saveAllMaterials(List<MaterialDto> materials){
        log.info("POST+++++++++++++++++{}", materials);
        List<MaterialEntity> validationList = new ArrayList<>();
        for(MaterialDto materialDto : materials) {
            //공급업체 있는지 확인
            SupplierEntity supplierEntity = supplierRepository.findById(materialDto.getSupplierId())
                    .orElse(null);

            if (supplierEntity != null) { //공급업체 있는 경우만
                MaterialEntity materialEntity = materialDto.toEntity(supplierEntity);
                validationList.add(materialEntity);
            } else {
                log.warn("존재 하지않음", materialDto.getSupplierId());
            }
        }
            //레파지토리 접근
        List<MaterialEntity> result = materialRepository.saveAll(validationList);

        return result.stream().map(MaterialDto::fromEntity).toList();

    }
    @Transactional
    public void deleteMaterial(Integer materialId) {
        materialRepository.deleteById(materialId);
    }
}
