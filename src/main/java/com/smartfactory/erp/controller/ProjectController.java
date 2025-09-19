package com.smartfactory.erp.controller;

import com.smartfactory.erp.dto.ProjectDto;
import com.smartfactory.erp.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    /**
     * 프로젝트 목록 동적 검색 (GET /api/projects)
     * 여러 검색 파라미터를 받아 서비스의 단일 검색 메소드를 호출합니다.
     */
    @GetMapping
    public ResponseEntity<List<ProjectDto>> searchProjects(
            @RequestParam(required = false) String projectId,
            @RequestParam(required = false) String projectNm,
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deliveryDate
    ) {
        List<ProjectDto> projects = projectService.searchProjects(projectId, projectNm, customerId, startDate, deliveryDate);
        return ResponseEntity.ok(projects); // HTTP 200 OK 와 함께 데이터 반환
    }

    /**
     * 단일 프로젝트 상세 조회 (GET /api/projects/{id})
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable String id) {
        ProjectDto project = projectService.findProjectById(id);
        return ResponseEntity.ok(project);
    }

    /**
     * 신규 프로젝트 생성 (POST /api/projects)
     */
    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectDto projectDto) {
        ProjectDto createdProject = projectService.createProject(projectDto);
        // HTTP 201 Created 상태 코드와 함께 생성된 데이터 반환
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    /**
     * 기존 프로젝트 수정 (PUT /api/projects/{id})
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProjectDto> updateProject(@PathVariable String id, @RequestBody ProjectDto projectDto) {
        ProjectDto updatedProject = projectService.updateProject(id, projectDto);
        return ResponseEntity.ok(updatedProject);
    }

    /**
     * 프로젝트 삭제 (DELETE /api/projects/{id})
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable String id) {
        projectService.deleteProject(id);
        // HTTP 204 No Content 상태 코드 반환 (성공적으로 처리했으나 반환할 내용이 없음)
        return ResponseEntity.noContent().build();
    }
}