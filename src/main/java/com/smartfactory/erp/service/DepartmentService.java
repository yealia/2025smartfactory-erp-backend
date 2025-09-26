package com.smartfactory.erp.service;

import com.smartfactory.erp.dto.DepartmentDto;
import com.smartfactory.erp.entity.DepartmentEntity;
import com.smartfactory.erp.repository.DepartmentRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    /**
     * 🔍 동적 검색 (부서ID, 부서명)
     */
    public List<DepartmentDto> searchDepartments(Integer departmentId,
                                                 String departmentNm,
                                                 String locationNm) {
        Specification<DepartmentEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (departmentId != null) {
                predicates.add(cb.equal(root.get("departmentId"), departmentId));
            }
            if (StringUtils.hasText(departmentNm)) {
                predicates.add(cb.like(root.get("departmentNm"), "%" + departmentNm + "%"));
            }

            if (StringUtils.hasText(locationNm)) {
                predicates.add(cb.like(root.get("locationNm"), "%" + locationNm + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return departmentRepository.findAll(spec).stream()
                .map(DepartmentDto::fromEntity)
                .toList();
    }

    // =========================
    // ✅ CRUD 기능
    // =========================

    // 단건 조회
    public DepartmentDto getDepartmentById(Integer departmentId) {
        return departmentRepository.findById(departmentId)
                .map(DepartmentDto::fromEntity)
                .orElse(null);
    }

    // 저장 (등록 & 수정)
    @Transactional
    public DepartmentDto saveDepartment(DepartmentDto dto) {
        DepartmentEntity entity = dto.toEntity();
        DepartmentEntity saved = departmentRepository.save(entity);
        return DepartmentDto.fromEntity(saved);
    }

    // 여러 건 저장
    @Transactional
    public List<DepartmentDto> saveAllDepartments(List<DepartmentDto> dtos) {
        List<DepartmentEntity> entities = dtos.stream()
                .map(DepartmentDto::toEntity)
                .toList();
        return departmentRepository.saveAll(entities).stream()
                .map(DepartmentDto::fromEntity)
                .toList();
    }

    // 삭제
    @Transactional
    public void deleteDepartment(Integer departmentId) {
        departmentRepository.deleteById(departmentId);
    }
}
