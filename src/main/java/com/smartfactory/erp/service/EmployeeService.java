package com.smartfactory.erp.service;

import com.smartfactory.erp.dto.EmployeeDto;
import com.smartfactory.erp.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    // 1. 조건이 둘 다 없을 경우
    public List<EmployeeDto> findAll() {
        return employeeRepository.findAll().stream()
                .map(EmployeeDto::fromEntity)
                .toList();
    }

    // 2. 사원명만 있을 경우
    public List<EmployeeDto> findByEmployeeName(String employeeNm) {
        return employeeRepository.findByEmployeeNmContaining(employeeNm).stream()
                .map(EmployeeDto::fromEntity)
                .toList();
    }

    // 3. 부서명만 있을 경우
    public List<EmployeeDto> findByDepartmentName(String departmentNm) {
        return employeeRepository.findByDepartment_DepartmentNmContaining(departmentNm).stream()
                .map(EmployeeDto::fromEntity)
                .toList();
    }


    // 4. 두 조건이 모두 있을 경우
    public List<EmployeeDto> findByEmployeeNameAndDepartmentName(String employeeNm, String departmentNm) {
        return employeeRepository.findByEmployeeNmContainingAndDepartment_DepartmentNmContaining(employeeNm, departmentNm).stream()
                .map(EmployeeDto::fromEntity)
                .toList();
    }
}