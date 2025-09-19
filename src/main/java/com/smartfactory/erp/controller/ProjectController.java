package com.smartfactory.erp.controller;

import com.smartfactory.erp.dto.ProjectDto;
import com.smartfactory.erp.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    // =========================
    // CREATE
    // =========================
    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectDto dto) {
        ProjectDto created = projectService.createProject(dto);
        return ResponseEntity.ok(created);
    }

    // =========================
    // READ (단일 조회)
    // =========================
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDto> getProject(@PathVariable String projectId) {
        ProjectDto dto = projectService.getProject(projectId);
        return ResponseEntity.ok(dto);
    }

    // =========================
    // UPDATE
    // =========================
    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectDto> updateProject(@PathVariable String projectId,
                                                    @RequestBody ProjectDto dto) {
        dto.setProjectId(projectId); // pathVariable 기준으로 ID 설정
        ProjectDto updated = projectService.updateProject(dto);
        return ResponseEntity.ok(updated);
    }

    // =========================
    // DELETE
    // =========================
    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable String projectId) {
        projectService.deleteProject(projectId);
        return ResponseEntity.noContent().build();
    }

    // =========================
    // DYNAMIC SEARCH
    // =========================
    @GetMapping
    public ResponseEntity<List<ProjectDto>> searchProjects(
            @RequestParam(required = false) String projectId,
            @RequestParam(required = false) String projectNm,
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deliveryDate
    ) {
        List<ProjectDto> list = projectService.searchProjects(projectId, projectNm, customerId, startDate, deliveryDate);
        return ResponseEntity.ok(list);
    }
}
