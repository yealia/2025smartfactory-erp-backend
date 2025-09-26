package com.smartfactory.erp.service;

import com.smartfactory.erp.dto.SupplierDto; // DTO는 별도로 생성해야 합니다.
import com.smartfactory.erp.entity.SupplierEntity;
import com.smartfactory.erp.repository.SupplierRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SupplierService {

    private final SupplierRepository supplierRepository;

    /**
     * 동적 조건으로 거래처 목록을 조회합니다.
     * @param supplierName 거래처명 (부분 일치 검색)
     * @param contractDate 계약일 (정확한 날짜 검색)
     * @return 조회된 거래처 DTO 목록
     */
    public List<SupplierDto> searchSuppliers(String supplierName, LocalDate contractDate) {
        // Specification을 사용하여 동적 쿼리 생성
        Specification<SupplierEntity> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 거래처명(supplierName) 조건: like 검색
            if (supplierName != null && !supplierName.trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("supplierName"), "%" + supplierName + "%"));
            }

            // 계약일(contractDate) 조건: 일치 검색
            if (contractDate != null) {
                predicates.add(criteriaBuilder.equal(root.get("contractDate"), contractDate));
            }

            // 모든 조건을 AND로 연결
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        List<SupplierEntity> entities = supplierRepository.findAll(spec);
        // Entity 리스트를 DTO 리스트로 변환하여 반환
        return entities.stream()
                .map(SupplierDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * ID로 특정 거래처를 조회합니다.
     * @param id 거래처 ID
     * @return 조회된 거래처 DTO
     */
    public SupplierDto getSupplierById(Integer id) {
        return supplierRepository.findById(id)
                .map(SupplierDto::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("해당 거래처를 찾을 수 없습니다: " + id));
    }

    /**
     * 신규 거래처를 등록하거나 기존 거래처 정보를 수정합니다.
     * @param supplierDto 등록/수정할 거래처 정보 DTO
     * @return 저장된 거래처 DTO
     */
    @Transactional
    public SupplierDto saveSupplier(SupplierDto supplierDto) {
        SupplierEntity entity = supplierDto.toEntity();
        SupplierEntity savedEntity = supplierRepository.save(entity);
        return SupplierDto.fromEntity(savedEntity);
    }

    /**
     * 여러 거래처를 한 번에 등록하거나 수정합니다.
     * @param supplierDtos 등록/수정할 거래처 DTO 목록
     * @return 저장된 거래처 DTO 목록
     */
    @Transactional
    public List<SupplierDto> saveAllSuppliers(List<SupplierDto> supplierDtos) {
        List<SupplierEntity> entities = supplierDtos.stream()
                .map(SupplierDto::toEntity)
                .collect(Collectors.toList());
        List<SupplierEntity> savedEntities = supplierRepository.saveAll(entities);
        return savedEntities.stream()
                .map(SupplierDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * ID로 특정 거래처를 삭제합니다.
     * @param id 삭제할 거래처 ID
     */
    @Transactional
    public void deleteSupplier(Integer id) {
        if (!supplierRepository.existsById(id)) {
            throw new IllegalArgumentException("삭제할 거래처가 존재하지 않습니다: " + id);
        }
        supplierRepository.deleteById(id);
    }
}
