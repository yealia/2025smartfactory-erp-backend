package com.smartfactory.erp.controller;

import com.smartfactory.erp.dto.EmployeeDto;
import com.smartfactory.erp.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * 🔍 동적 검색 (사원명, 부서명)
     * - /api/employees?employeeNm=홍길동&departmentNm=생산팀
     */
    @GetMapping
    public List<EmployeeDto> searchEmployees(
            @RequestParam(required = false) String employeeNm,
            @RequestParam(required = false) String departmentNm) {
        return employeeService.searchEmployees(employeeNm, departmentNm);
    }

    /**
     * 👀 단건 조회
     */
    @GetMapping("/{employeeId}")
    public EmployeeDto getEmployee(@PathVariable String employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    /**
     * ➕ 단건 저장 (등록 & 수정)
     */
    @PostMapping
    public EmployeeDto saveEmployee(@RequestBody EmployeeDto employeeDto) {
        return employeeService.saveEmployee(employeeDto);
    }

    /**
     * 📦 여러 건 저장
     */
    @PostMapping("/saveAll")
    public List<EmployeeDto> saveAllEmployees(@RequestBody List<EmployeeDto> employeeDtos) {
        return employeeService.saveAllEmployees(employeeDtos);
    }

    /**
     * 📝 수정
     */
    @PutMapping("/{employeeId}")
    public EmployeeDto updateEmployee(@PathVariable String employeeId, @RequestBody EmployeeDto employeeDto) {
        employeeDto.setEmployeeId(employeeId); // 경로 ID를 DTO에 주입
        return employeeService.saveEmployee(employeeDto);
    }

    /**
     * 🗑️ 삭제
     */
    @DeleteMapping("/{employeeId}")
    public void deleteEmployee(@PathVariable String employeeId) {
        log.info("삭제 요청 ID = {}", employeeId);
        employeeService.deleteEmployee(employeeId);
    }
}
