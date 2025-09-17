package com.smartfactory.erp.controller;

import com.smartfactory.erp.dto.DepartmentDto;
import com.smartfactory.erp.dto.EmployeeDto;
import com.smartfactory.erp.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public List<EmployeeDto> getEmployees(String employeeNm, String departmentNm){

        // 4. 두 조건이 모두 있는 경우
        if ( employeeNm != null && departmentNm != null ) {
            return employeeService. findByEmployeeNameAndDepartmentName(employeeNm, departmentNm);
        }
        // 2. 사원명만 있는 경우
        else if (employeeNm != null) {
            return employeeService.findByEmployeeName(employeeNm);
        }
        // 3. 부서명만 있는 경우
        else if (departmentNm != null) {
            return employeeService.findByDepartmentName(departmentNm);
        }
        // 1. 아무 조건도 없는 경우
        else {
            return employeeService.findAll();
        }

    }

}