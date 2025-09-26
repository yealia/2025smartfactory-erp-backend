package com.smartfactory.erp.controller;

import com.smartfactory.erp.dto.ProjectPlanDto;
import com.smartfactory.erp.service.ProjectPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/project_plans")
@RequiredArgsConstructor
public class ProjectPlanController {

    private final ProjectPlanService projectPlanService;

    /**
     * 신규 프로젝트 계획 생성 (Create)
     * @param projectPlanDto 생성할 데이터
     * @return 생성된 데이터와 HTTP 201 Created 상태 코드
     */
    @PostMapping
    public ResponseEntity<ProjectPlanDto> createPlan(@RequestBody ProjectPlanDto projectPlanDto) {
        ProjectPlanDto createdPlan = projectPlanService.createPlan(projectPlanDto);
        return new ResponseEntity<>(createdPlan, HttpStatus.CREATED);
    }

    /**
     * 모든 프로젝트 계획 조회 및 동적 검색 (Read)
     * @param projectId (선택) 검색할 프로젝트 ID
     * @param vesselId (선택) 검색할 선박 ID
     * @param startDate (선택) 검색할 시작일
     * @param endDate (선택) 검색할 종료일
     * @param status (선택) 검색할 상태
     * @return 검색 조건에 맞는 데이터 목록
     */
    @GetMapping
    public ResponseEntity<List<ProjectPlanDto>> searchPlans(
            @RequestParam(required = false) String projectId,
            @RequestParam(required = false) String vesselId,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) Integer status) {

        // 모든 파라미터를 서비스의 단일 search 메서드로 전달
        List<ProjectPlanDto> results = projectPlanService.search(projectId, vesselId, startDate, endDate, status);
        return ResponseEntity.ok(results);
    }

    /**
     * 특정 ID의 프로젝트 계획 조회 (Read)
     * @param planId 조회할 계획 ID
     * @return 조회된 데이터
     */
    @GetMapping("/{planId}")
    public ResponseEntity<ProjectPlanDto> getPlanById(@PathVariable String planId) {
        return ResponseEntity.ok(projectPlanService.getPlanById(planId));
    }

    /**
     * 프로젝트 계획 정보 수정 (Update)
     * @param planId 수정할 계획 ID
     * @param projectPlanDto 수정할 내용이 담긴 데이터
     * @return 수정된 데이터
     */
    @PutMapping("/{planId}")
    public ResponseEntity<ProjectPlanDto> updatePlan(@PathVariable String planId, @RequestBody ProjectPlanDto projectPlanDto) {
        ProjectPlanDto updatedPlan = projectPlanService.updatePlan(planId, projectPlanDto);
        return ResponseEntity.ok(updatedPlan);
    }

    /**
     * 프로젝트 계획 삭제 (Delete)
     * @param planId 삭제할 계획 ID
     * @return HTTP 204 No Content 상태 코드
     */
    @DeleteMapping("/{planId}")
    public ResponseEntity<Void> deletePlan(@PathVariable String planId) {
        projectPlanService.deletePlan(planId);
        return ResponseEntity.noContent().build();
    }
}
