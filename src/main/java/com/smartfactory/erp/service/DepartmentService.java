package com.smartfactory.erp.service;

import com.smartfactory.erp.dto.DepartmentDto;
import com.smartfactory.erp.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    // 1. 조건이 둘 다 없을 경우
    public List<DepartmentDto> findAll() {
        return departmentRepository.findAll().stream()
                .map(DepartmentDto::fromEntity)
                .toList();
    }

    // 2. 부서 ID만 있을 경우
    public List<DepartmentDto> findById(int departmentId) {
        return departmentRepository.findById(departmentId)
                .map(entity -> List.of(DepartmentDto.fromEntity(entity)))
                .orElse(Collections.emptyList());
    }

    // 3. 부서명만 있을 경우
    public List<DepartmentDto> findByName(String departmentNm) {
        return departmentRepository.findByDepartmentNmContaining(departmentNm).stream()
                .map(DepartmentDto::fromEntity)
                .toList();
    }

    // 4. 두 조건이 모두 있을 경우
    public List<DepartmentDto> findByIdAndName(int departmentId, String departmentNm) {
        return departmentRepository.findByDepartmentIdAndDepartmentNmContaining(departmentId, departmentNm).stream()
                .map(DepartmentDto::fromEntity)
                .toList();
    }
}