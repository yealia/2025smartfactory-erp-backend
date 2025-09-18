package com.smartfactory.erp.controller;

import com.smartfactory.erp.dto.ProjectPlanDto;
import com.smartfactory.erp.service.ProjectPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/project_plans")
@RequiredArgsConstructor
public class ProjectPlanController {

    private final ProjectPlanService projectPlanService;

    @GetMapping
    public List<ProjectPlanDto> getProjects(String projectId, String vesselId, LocalDate startDate, LocalDate endDate, Integer status) {

        // --- 조건이 5개인 경우 ---
        if (projectId != null && vesselId != null && startDate != null && endDate != null && status != null) {
            return projectPlanService.searchByAllConditions(projectId, vesselId, startDate, endDate, status);
        }

        // --- 조건이 4개인 경우 ---
        else if (projectId != null && vesselId != null && startDate != null && endDate != null) {
            return projectPlanService.searchByProjectIdAndVesselIdAndStartDateAndEndDate(projectId, vesselId, startDate, endDate);
        } else if (projectId != null && vesselId != null && status != null) {
            return projectPlanService.searchByProjectIdAndVesselIdAndStatus(projectId, vesselId, status);
        } else if (projectId != null && startDate != null && endDate != null && status != null) {
            return projectPlanService.searchByProjectIdAndStartDateAndEndDateAndStatus(projectId, startDate, endDate, status);
        } else if (vesselId != null && startDate != null && endDate != null && status != null) {
            return projectPlanService.searchByVesselIdAndStartDateAndEndDateAndStatus(vesselId, startDate, endDate, status);
        }

        // --- 조건이 3개인 경우 ---
        else if (projectId != null && vesselId != null) {
            return projectPlanService.searchByProjectIdAndVesselId(projectId, vesselId);
        } else if (projectId != null && startDate != null && endDate != null) {
            return projectPlanService.searchByProjectIdAndStartDateAndEndDate(projectId, startDate, endDate);
        } else if (projectId != null && status != null) {
            return projectPlanService.searchByProjectIdAndStatus(projectId, status);
        } else if (vesselId != null && startDate != null && endDate != null) {
            return projectPlanService.searchByVesselIdAndStartDateAndEndDate(vesselId, startDate, endDate);
        } else if (vesselId != null && status != null) {
            return projectPlanService.searchByVesselIdAndStatus(vesselId, status);
        } else if (startDate != null && endDate != null && status != null) {
            return projectPlanService.searchByStartDateAndEndDateAndStatus(startDate, endDate, status);
        }

        // --- 조건이 2개인 경우 ---
        else if (projectId != null) {
            return projectPlanService.searchByProjectId(projectId);
        } else if (vesselId != null) {
            return projectPlanService.searchByVesselId(vesselId);
        } else if (startDate != null) {
            return projectPlanService.searchByStartDate(startDate);
        } else if (endDate != null) {
            return projectPlanService.searchByEndDate(endDate);
        } else if (status != null) {
            return projectPlanService.searchByStatus(status);
        }

        // --- 조건이 없는 경우 (전체 조회) ---
        else {
            return projectPlanService.searchAllPlans();
        }
    }
}
