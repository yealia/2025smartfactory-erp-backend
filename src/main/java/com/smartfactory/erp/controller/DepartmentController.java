package com.smartfactory.erp.controller;

import com.smartfactory.erp.dto.DepartmentDto;
import com.smartfactory.erp.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public List<DepartmentDto> getDepartments( Integer departmentId, String departmentNm){

        // 4. 두 조건이 모두 있는 경우
        if (departmentId != null && departmentNm != null) {
            return departmentService.findByIdAndName(departmentId, departmentNm);
        }
        // 2. 부서 ID만 있는 경우
        else if (departmentId != null) {
            return departmentService.findById(departmentId);
        }
        // 3. 부서명만 있는 경우
        else if (departmentNm != null) {
            return departmentService.findByName(departmentNm);
        }
        // 1. 아무 조건도 없는 경우
        else {
            return departmentService.findAll();
        }

    }
}