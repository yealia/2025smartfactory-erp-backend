package com.smartfactory.erp.controller;

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
    public List<ProjectDto> getProjects(
            String projectId,
            String projectNm,
            String customerId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deliveryDate
    ) {
        // --- 조건이 5개인 경우 ---
        if (projectId != null && projectNm != null && customerId != null && startDate != null && deliveryDate != null) {
            return projectService.searchByAllConditions(projectId, projectNm, customerId, startDate, deliveryDate);
        }
        // --- 조건이 4개인 경우 ---
        else if (projectId != null && projectNm != null && customerId != null && startDate != null) {
            return projectService.searchByProjectIdAndProjectNmAndCustomerIdAndStartDate(projectId, projectNm, customerId, startDate);
        } else if (projectId != null && projectNm != null && customerId != null && deliveryDate != null) {
            return projectService.searchByProjectIdAndProjectNmAndCustomerIdAndDeliveryDate(projectId, projectNm, customerId, deliveryDate);
        } else if (projectId != null && projectNm != null && startDate != null && deliveryDate != null) {
            return projectService.searchByProjectIdAndProjectNmAndStartDateAndDeliveryDate(projectId, projectNm, startDate, deliveryDate);
        } else if (projectId != null && customerId != null && startDate != null && deliveryDate != null) {
            return projectService.searchByProjectIdAndCustomerIdAndStartDateAndDeliveryDate(projectId, customerId, startDate, deliveryDate);
        } else if (projectNm != null && customerId != null && startDate != null && deliveryDate != null) {
            return projectService.searchByProjectNmAndCustomerIdAndStartDateAndDeliveryDate(projectNm, customerId, startDate, deliveryDate);
        }
        // --- 조건이 3개인 경우 ---
        else if (projectId != null && projectNm != null && customerId != null) {
            return projectService.searchByProjectIdAndProjectNmAndCustomerId(projectId, projectNm, customerId);
        } else if (projectId != null && projectNm != null && startDate != null) {
            return projectService.searchByProjectIdAndProjectNmAndStartDate(projectId, projectNm, startDate);
        } else if (projectId != null && projectNm != null && deliveryDate != null) {
            return projectService.searchByProjectIdAndProjectNmAndDeliveryDate(projectId, projectNm, deliveryDate);
        } else if (projectId != null && customerId != null && startDate != null) {
            return projectService.searchByProjectIdAndCustomerIdAndStartDate(projectId, customerId, startDate);
        } else if (projectId != null && customerId != null && deliveryDate != null) {
            return projectService.searchByProjectIdAndCustomerIdAndDeliveryDate(projectId, customerId, deliveryDate);
        } else if (projectId != null && startDate != null && deliveryDate != null) {
            return projectService.searchByProjectIdAndStartDateAndDeliveryDate(projectId, startDate, deliveryDate);
        } else if (projectNm != null && customerId != null && startDate != null) {
            return projectService.searchByProjectNmAndCustomerIdAndStartDate(projectNm, customerId, startDate);
        } else if (projectNm != null && customerId != null && deliveryDate != null) {
            return projectService.searchByProjectNmAndCustomerIdAndDeliveryDate(projectNm, customerId, deliveryDate);
        } else if (projectNm != null && startDate != null && deliveryDate != null) {
            return projectService.searchByProjectNmAndStartDateAndDeliveryDate(projectNm, startDate, deliveryDate);
        } else if (customerId != null && startDate != null && deliveryDate != null) {
            return projectService.searchByCustomerIdAndStartDateAndDeliveryDate(customerId, startDate, deliveryDate);
        }
        // --- 조건이 2개인 경우 ---
        else if (projectId != null && projectNm != null) {
            return projectService.searchByProjectIdAndProjectNm(projectId, projectNm);
        } else if (projectId != null && customerId != null) {
            return projectService.searchByProjectIdAndCustomerId(projectId, customerId);
        } else if (projectId != null && startDate != null) {
            return projectService.searchByProjectIdAndStartDate(projectId, startDate);
        } else if (projectId != null && deliveryDate != null) {
            return projectService.searchByProjectIdAndDeliveryDate(projectId, deliveryDate);
        } else if (projectNm != null && customerId != null) {
            return projectService.searchByProjectNmAndCustomerId(projectNm, customerId);
        } else if (projectNm != null && startDate != null) {
            return projectService.searchByProjectNmAndStartDate(projectNm, startDate);
        } else if (projectNm != null && deliveryDate != null) {
            return projectService.searchByProjectNmAndDeliveryDate(projectNm, deliveryDate);
        } else if (customerId != null && startDate != null) {
            return projectService.searchByCustomerIdAndStartDate(customerId, startDate);
        } else if (customerId != null && deliveryDate != null) {
            return projectService.searchByCustomerIdAndDeliveryDate(customerId, deliveryDate);
        } else if (startDate != null && deliveryDate != null) {
            return projectService.searchByStartDateAndDeliveryDate(startDate, deliveryDate);
        }
        // --- 조건이 1개인 경우 ---
        else if (projectId != null) {
            ProjectDto project = projectService.searchByProjectId(projectId);
            return project != null ? List.of(project) : List.of();
        } else if (projectNm != null) {
            return projectService.searchByProjectNm(projectNm);
        } else if (customerId != null) {
            return projectService.searchByCustomerId(customerId);
        } else if (startDate != null) {
            return projectService.searchByStartDate(startDate);
        } else if (deliveryDate != null) {
            return projectService.searchByDeliveryDate(deliveryDate);
        }
        // --- 조건이 없는 경우 ---
        else {
            return projectService.searchAllProjects();
        }
    }
}
