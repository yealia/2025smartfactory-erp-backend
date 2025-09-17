package com.smartfactory.erp.controller;

import com.smartfactory.erp.dto.CustomerDto;
import com.smartfactory.erp.dto.ProjectDto;
import com.smartfactory.erp.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    // 조건 검색
    @GetMapping
    public List<ProjectDto> getProjects(String projectId, String projectNm, String customerId, LocalDate startDate, LocalDate deliveryDate){
        if (projectId != null && projectNm != null && customerId != null && startDate != null && deliveryDate != null) {
            return projectService.searchByAllConditions(projectId, projectNm, customerId, startDate, deliveryDate);
        }
        // --- 조건이 3개인 경우 ---
        else if (projectId != null && projectNm != null && customerId != null) {
            return projectService.searchByProjectIdAndProjectNmAndCustomerId(projectId, projectNm, customerId);
        } else if (projectId != null && projectNm != null && startDate != null && deliveryDate != null) {
            return projectService.searchByProjectIdAndProjectNmAndDateRange(projectId, projectNm, startDate, deliveryDate);
        } else if (projectId != null && customerId != null && startDate != null && deliveryDate != null) {
            return projectService.searchByProjectIdAndCustomerIdAndDateRange(projectId, customerId, startDate, deliveryDate);
        } else if (projectNm != null && customerId != null && startDate != null && deliveryDate != null) {
            return projectService.searchByProjectNmAndCustomerIdAndDateRange(projectNm, customerId, startDate, deliveryDate);
        }
        // --- 조건이 2개인 경우 ---
        else if (projectId != null && projectNm != null) {
            return projectService.searchByProjectIdAndProjectNm(projectId, projectNm);
        } else if (projectId != null && customerId != null) {
            return projectService.searchByProjectIdAndCustomerId(projectId, customerId);
        } else if (projectId != null && startDate != null && deliveryDate != null) {
            return projectService.searchByProjectIdAndDateRange(projectId, startDate, deliveryDate);
        } else if (projectNm != null && customerId != null) {
            return projectService.searchByProjectNmAndCustomerId(projectNm, customerId);
        } else if (projectNm != null && startDate != null && deliveryDate != null) {
            return projectService.searchByProjectNmAndDateRange(projectNm, startDate, deliveryDate);
        } else if (customerId != null && startDate != null && deliveryDate != null) {
            return projectService.searchByCustomerIdAndDateRange(customerId, startDate, deliveryDate);
        }
        // --- 조건이 1개인 경우 ---
          else if (projectNm != null) {
            return projectService.searchByProjectNm(projectNm);
        } else if (customerId != null) {
            return projectService.searchByCustomerId(customerId);
        } else if (startDate != null && deliveryDate != null) {
            return projectService.searchByDateRange(startDate, deliveryDate);
        } else if (projectId != null) {
            ProjectDto project = projectService.searchByProject(projectId);
            // 단일 객체를 List에 담아서 반환해야 프론트엔드에서 일관되게 처리 가능
            return project != null ? List.of(project) : List.of();
        }
        // --- 조건이 없는 경우 ---
            else {
            return projectService.searchAllProjects();
        }
    }
}
