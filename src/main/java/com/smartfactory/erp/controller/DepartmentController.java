package com.smartfactory.erp.controller;

import com.smartfactory.erp.dto.DepartmentDto;
import com.smartfactory.erp.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    /**
     * 🔍 동적 검색 (부서ID / 부서명)
     */
    @GetMapping
    public List<DepartmentDto> searchDepartments(
            @RequestParam(required = false) Integer departmentId,
            @RequestParam(required = false) String departmentNm) {
        return departmentService.searchDepartments(departmentId, departmentNm);
    }

    /**
     * 👀 단건 조회
     */
    @GetMapping("/{departmentId}")
    public DepartmentDto getDepartment(@PathVariable Integer departmentId) {
        return departmentService.getDepartmentById(departmentId);
    }

    /**
     * ➕ 등록 & 수정
     */
    @PostMapping
    public DepartmentDto saveDepartment(@RequestBody DepartmentDto dto) {
        return departmentService.saveDepartment(dto);
    }

    /**
     * 📦 여러 건 저장
     */
    @PostMapping("/saveAll")
    public List<DepartmentDto> saveAllDepartments(@RequestBody List<DepartmentDto> dtos) {
        return departmentService.saveAllDepartments(dtos);
    }

    /**
     * 📝 수정
     */
    @PutMapping("/{departmentId}")
    public DepartmentDto updateDepartment(
            @PathVariable Integer departmentId,
            @RequestBody DepartmentDto dto) {
        dto.setDepartmentId(departmentId);
        return departmentService.saveDepartment(dto);
    }

    /**
     * 🗑️ 삭제
     */
    @DeleteMapping("/{departmentId}")
    public void deleteDepartment(@PathVariable Integer departmentId) {
        log.info("삭제 요청 ID = {}", departmentId);
        departmentService.deleteDepartment(departmentId);
    }
}
