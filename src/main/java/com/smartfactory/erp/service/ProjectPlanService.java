package com.smartfactory.erp.service;

import com.smartfactory.erp.dto.ProjectPlanDto;
import com.smartfactory.erp.repository.ProjectPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ProjectPlanService {

    private final ProjectPlanRepository projectPlanRepository;
// =========================
// 조건별 검색 메서드
// =========================
// 조건이 없을 때 (전체 조회)

    public List<ProjectPlanDto> searchAllPlans() {
        return projectPlanRepository.findAll().stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }
// --- 조건이 1개인 경우 ---

    public List<ProjectPlanDto> searchByProjectId(String projectId) {
        return projectPlanRepository.findByProjectId(projectId).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByVesselId(String vesselId) {
        return projectPlanRepository.findByVesselId(vesselId).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByDateRange(LocalDate startDate, LocalDate endDate) {
        return projectPlanRepository.findByStartDateBetween(startDate, endDate).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByStatus(Integer status) {
        return projectPlanRepository.findByStatus(status).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

// --- 조건이 2개인 경우 ---
    public List<ProjectPlanDto> searchByProjectIdAndVesselId(String projectId, String vesselId) {
        return projectPlanRepository.findByProjectIdAndVesselId(projectId, vesselId).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByProjectIdAndDateRange(String projectId, LocalDate startDate, LocalDate endDate) {
        return projectPlanRepository.findByProjectIdAndStartDateBetween(projectId, startDate, endDate).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByProjectIdAndStatus(String projectId, Integer status) {
        return projectPlanRepository.findByProjectIdAndStatus(projectId, status).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByVesselIdAndDateRange(String vesselId, LocalDate startDate, LocalDate endDate) {
        return projectPlanRepository.findByVesselIdAndStartDateBetween(vesselId, startDate, endDate).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByVesselIdAndStatus(String vesselId, Integer status) {
        return projectPlanRepository.findByVesselIdAndStatus(vesselId, status).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByDateRangeAndStatus(LocalDate startDate, LocalDate endDate, Integer status) {
        return projectPlanRepository.findByStartDateBetweenAndStatus(startDate, endDate, status).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

// --- 조건이 3개인 경우 ---
    public List<ProjectPlanDto> searchByProjectIdAndVesselIdAndDateRange(String projectId, String vesselId, LocalDate startDate, LocalDate endDate) {
        return projectPlanRepository.findByProjectIdAndVesselIdAndStartDateBetween(projectId, vesselId, startDate, endDate).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByProjectIdAndVesselIdAndStatus(String projectId, String vesselId, Integer status) {
        return projectPlanRepository.findByProjectIdAndVesselIdAndStatus(projectId, vesselId, status).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByProjectIdAndDateRangeAndStatus(String projectId, LocalDate startDate, LocalDate endDate, Integer status) {
        return projectPlanRepository.findByProjectIdAndStartDateBetweenAndStatus(projectId, startDate, endDate, status).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByVesselIdAndDateRangeAndStatus(String vesselId, LocalDate startDate, LocalDate endDate, Integer status) {
        return projectPlanRepository.findByVesselIdAndStartDateBetweenAndStatus(vesselId, startDate, endDate, status).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

// --- 조건이 4개인 경우 ---
    public List<ProjectPlanDto> searchByAllConditions(String projectId, String vesselId, LocalDate startDate, LocalDate endDate, Integer status) {
        return projectPlanRepository.findByProjectIdAndVesselIdAndStartDateBetweenAndStatus(projectId, vesselId, startDate, endDate, status).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }
}