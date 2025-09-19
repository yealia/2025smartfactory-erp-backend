package com.smartfactory.erp.service;

import com.smartfactory.erp.dto.EmployeeDto;
import com.smartfactory.erp.entity.EmployeeEntity;
import com.smartfactory.erp.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    /**
     * ✅ 동적 검색
     */
    public List<EmployeeDto> searchEmployees(String employeeId, String employeeNm) {
        Specification<EmployeeEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 사원 ID 검색 조건 추가
            if (StringUtils.hasText(employeeId)) {
                predicates.add(cb.like(root.get("employeeId"), "%" + employeeId + "%"));
            }

            // 사원명 검색 조건 추가
            if (StringUtils.hasText(employeeNm)) {
                predicates.add(cb.like(root.get("employeeNm"), "%" + employeeNm + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return employeeRepository.findAll(spec).stream()
                .map(EmployeeDto::fromEntity)
                .toList();
    }

    // =========================
    // ✅ CRUD 기능
    // =========================

    // 단건 조회
    public EmployeeDto getEmployeeById(String employeeId) {
        return employeeRepository.findById(employeeId)
                .map(EmployeeDto::fromEntity)
                .orElse(null);
    }

    // 저장 (등록 & 수정)
    @Transactional
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        EmployeeEntity entity = employeeDto.toEntity();   // DTO → Entity
        EmployeeEntity saved = employeeRepository.save(entity);
        return EmployeeDto.fromEntity(saved);             // Entity → DTO
    }

    // 여러 건 저장
    @Transactional
    public List<EmployeeDto> saveAllEmployees(List<EmployeeDto> employeeDtos) {
        List<EmployeeEntity> entities = employeeDtos.stream()
                .map(EmployeeDto::toEntity)
                .toList();
        List<EmployeeEntity> savedEntities = employeeRepository.saveAll(entities);
        return savedEntities.stream()
                .map(EmployeeDto::fromEntity)
                .toList();
    }

    // 삭제
    @Transactional
    public void deleteEmployee(String employeeId) {
        employeeRepository.deleteById(employeeId);
    }
}
