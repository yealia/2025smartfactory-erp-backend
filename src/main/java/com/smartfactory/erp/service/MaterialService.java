package com.smartfactory.erp.service;

import com.smartfactory.erp.dto.MaterialDto; // DTO는 별도로 생성해야 합니다.
import com.smartfactory.erp.entity.MaterialEntity;
import com.smartfactory.erp.entity.SupplierEntity;
import com.smartfactory.erp.repository.MaterialRepository;
import com.smartfactory.erp.repository.SupplierRepository; // 공급업체 조회를 위해 주입
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MaterialService {

    private final MaterialRepository materialRepository;
    private final SupplierRepository supplierRepository; // ✅ 공급업체 관계 설정을 위해 주입

    /**
     * 자재명(materialNm)과 카테고리(category)를 조건으로 자재 목록을 동적으로 검색합니다.
     */
    public List<MaterialDto> searchMaterials(String materialNm, String category) {
        // Specification을 사용하여 동적 쿼리 생성
        Specification<MaterialEntity> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 자재명 조건 (값이 있을 경우에만 like 검색 추가)
            if (materialNm != null && !materialNm.trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("materialNm"), "%" + materialNm + "%"));
            }

            // 카테고리 조건 (값이 있을 경우에만 like 검색 추가)
            if (category != null && !category.trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("category"), "%" + category + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        List<MaterialEntity> entities = materialRepository.findAll(spec);
        // Entity 리스트를 DTO 리스트로 변환하여 반환
        return entities.stream()
                .map(MaterialDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * ID로 특정 자재 정보를 조회합니다.
     */
    public MaterialDto getMaterialById(Integer id) {
        return materialRepository.findById(id)
                .map(MaterialDto::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 자재를 찾을 수 없습니다: " + id));
    }

    /**
     * 신규 자재를 등록하거나 기존 자재 정보를 수정합니다.
     */
    @Transactional
    public MaterialDto saveMaterial(MaterialDto materialDto) {
        // 1. DTO를 기본 엔티티로 변환
        MaterialEntity entity = materialDto.toEntity();

        // 2. supplierId가 있다면 ID로 매핑
        if (materialDto.getSupplierId() != null) {
            SupplierEntity supplier = supplierRepository.findById(materialDto.getSupplierId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 ID의 공급업체를 찾을 수 없습니다: " + materialDto.getSupplierId()));
            entity.setSupplier(supplier);
        }
        // 3. supplierName이 있다면 이름으로 매핑
        else if (materialDto.getSupplierName() != null && !materialDto.getSupplierName().trim().isEmpty()) {
            SupplierEntity supplier = supplierRepository.findBySupplierName(materialDto.getSupplierName())
                    .orElseThrow(() -> new IllegalArgumentException("해당 공급업체명을 찾을 수 없습니다: " + materialDto.getSupplierName()));
            entity.setSupplier(supplier);
        }

        // 4. 저장 후 DTO로 변환 반환
        MaterialEntity savedEntity = materialRepository.save(entity);
        return MaterialDto.fromEntity(savedEntity);
    }


    /**
     * ID로 특정 자재 정보를 삭제합니다.
     */
    @Transactional
    public void deleteMaterial(Integer id) {
        if (!materialRepository.existsById(id)) {
            throw new IllegalArgumentException("삭제할 자재가 존재하지 않습니다: " + id);
        }
        materialRepository.deleteById(id);
    }
}

