package com.smartfactory.erp.service;

import com.smartfactory.erp.dto.ProjectPlanDto;
import com.smartfactory.erp.repository.ProjectPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
        return projectPlanRepository.findByProject_ProjectId(projectId).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByVesselId(String vesselId) {
        return projectPlanRepository.findByVessel_VesselId(vesselId).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByStartDate(LocalDate startDate) {
        return projectPlanRepository.findByStartDate(startDate).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByEndDate(LocalDate endDate) {
        return projectPlanRepository.findByEndDate(endDate).stream()
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
        return projectPlanRepository.findByProject_ProjectIdAndVessel_VesselId(projectId, vesselId).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByProjectIdAndStartDate(String projectId, LocalDate startDate) {
        return projectPlanRepository.findByProject_ProjectIdAndStartDate(projectId, startDate).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByProjectIdAndEndDate(String projectId, LocalDate endDate) {
        return projectPlanRepository.findByProject_ProjectIdAndEndDate(projectId, endDate).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByProjectIdAndStatus(String projectId, Integer status) {
        return projectPlanRepository.findByProject_ProjectIdAndStatus(projectId, status).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByVesselIdAndStartDate(String vesselId, LocalDate startDate) {
        return projectPlanRepository.findByVessel_VesselIdAndStartDate(vesselId, startDate).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByVesselIdAndEndDate(String vesselId, LocalDate endDate) {
        return projectPlanRepository.findByVessel_VesselIdAndEndDate(vesselId, endDate).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByVesselIdAndStatus(String vesselId, Integer status) {
        return projectPlanRepository.findByVessel_VesselIdAndStatus(vesselId, status).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByStartDateAndEndDate(LocalDate startDate, LocalDate endDate) {
        return projectPlanRepository.findByStartDateAndEndDate(startDate, endDate).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByStartDateAndStatus(LocalDate startDate, Integer status) {
        return projectPlanRepository.findByStartDateAndStatus(startDate, status).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByEndDateAndStatus(LocalDate endDate, Integer status) {
        return projectPlanRepository.findByEndDateAndStatus(endDate, status).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    // --- 조건이 3개인 경우 ---
    public List<ProjectPlanDto> searchByProjectIdAndVesselIdAndStartDate(String projectId, String vesselId, LocalDate startDate) {
        return projectPlanRepository.findByProject_ProjectIdAndVessel_VesselIdAndStartDate(projectId, vesselId, startDate).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByProjectIdAndVesselIdAndEndDate(String projectId, String vesselId, LocalDate endDate) {
        return projectPlanRepository.findByProject_ProjectIdAndVessel_VesselIdAndEndDate(projectId, vesselId, endDate).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByProjectIdAndVesselIdAndStatus(String projectId, String vesselId, Integer status) {
        return projectPlanRepository.findByProject_ProjectIdAndVessel_VesselIdAndStatus(projectId, vesselId, status).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByProjectIdAndStartDateAndEndDate(String projectId, LocalDate startDate, LocalDate endDate) {
        return projectPlanRepository.findByProject_ProjectIdAndStartDateAndEndDate(projectId, startDate, endDate).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByProjectIdAndStartDateAndStatus(String projectId, LocalDate startDate, Integer status) {
        return projectPlanRepository.findByProject_ProjectIdAndStartDateAndStatus(projectId, startDate, status).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByProjectIdAndEndDateAndStatus(String projectId, LocalDate endDate, Integer status) {
        return projectPlanRepository.findByProject_ProjectIdAndEndDateAndStatus(projectId, endDate, status).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByVesselIdAndStartDateAndEndDate(String vesselId, LocalDate startDate, LocalDate endDate) {
        return projectPlanRepository.findByVessel_VesselIdAndStartDateAndEndDate(vesselId, startDate, endDate).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByVesselIdAndStartDateAndStatus(String vesselId, LocalDate startDate, Integer status) {
        return projectPlanRepository.findByVessel_VesselIdAndStartDateAndStatus(vesselId, startDate, status).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByVesselIdAndEndDateAndStatus(String vesselId, LocalDate endDate, Integer status) {
        return projectPlanRepository.findByVessel_VesselIdAndEndDateAndStatus(vesselId, endDate, status).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByStartDateAndEndDateAndStatus(LocalDate startDate, LocalDate endDate, Integer status) {
        return projectPlanRepository.findByStartDateAndEndDateAndStatus(startDate, endDate, status).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    // --- 조건이 4개인 경우 ---
    public List<ProjectPlanDto> searchByProjectIdAndVesselIdAndStartDateAndEndDate(String projectId, String vesselId, LocalDate startDate, LocalDate endDate) {
        return projectPlanRepository.findByProject_ProjectIdAndVessel_VesselIdAndStartDateAndEndDate(projectId, vesselId, startDate, endDate).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByProjectIdAndVesselIdAndStartDateAndStatus(String projectId, String vesselId, LocalDate startDate, Integer status) {
        return projectPlanRepository.findByProject_ProjectIdAndVessel_VesselIdAndStartDateAndStatus(projectId, vesselId, startDate, status).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByProjectIdAndVesselIdAndEndDateAndStatus(String projectId, String vesselId, LocalDate endDate, Integer status) {
        return projectPlanRepository.findByProject_ProjectIdAndVessel_VesselIdAndEndDateAndStatus(projectId, vesselId, endDate, status).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByProjectIdAndStartDateAndEndDateAndStatus(String projectId, LocalDate startDate, LocalDate endDate, Integer status) {
        return projectPlanRepository.findByProject_ProjectIdAndStartDateAndEndDateAndStatus(projectId, startDate, endDate, status).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    public List<ProjectPlanDto> searchByVesselIdAndStartDateAndEndDateAndStatus(String vesselId, LocalDate startDate, LocalDate endDate, Integer status) {
        return projectPlanRepository.findByVessel_VesselIdAndStartDateAndEndDateAndStatus(vesselId, startDate, endDate, status).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }

    // --- 조건이 5개인 경우 ---
    public List<ProjectPlanDto> searchByAllConditions(String projectId, String vesselId, LocalDate startDate, LocalDate endDate, Integer status) {
        return projectPlanRepository.findByProject_ProjectIdAndVessel_VesselIdAndStartDateAndEndDateAndStatus(projectId, vesselId, startDate, endDate, status).stream()
                .map(ProjectPlanDto::fromEntity)
                .toList();
    }
}
