package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.ProjectPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProjectPlanRepository extends JpaRepository<ProjectPlanEntity, String> {

    // 1개 조건
    List<ProjectPlanEntity> findByProject_ProjectId(String projectId);
    List<ProjectPlanEntity> findByVessel_VesselId(String vesselId);
    List<ProjectPlanEntity> findByStartDate(LocalDate startDate);
    List<ProjectPlanEntity> findByEndDate(LocalDate endDate);
    List<ProjectPlanEntity> findByStatus(Integer status);

    // 2개 조건
    List<ProjectPlanEntity> findByProject_ProjectIdAndVessel_VesselId(String projectId, String vesselId);
    List<ProjectPlanEntity> findByProject_ProjectIdAndStartDate(String projectId, LocalDate startDate);
    List<ProjectPlanEntity> findByProject_ProjectIdAndEndDate(String projectId, LocalDate endDate);
    List<ProjectPlanEntity> findByProject_ProjectIdAndStatus(String projectId, Integer status);
    List<ProjectPlanEntity> findByVessel_VesselIdAndStartDate(String vesselId, LocalDate startDate);
    List<ProjectPlanEntity> findByVessel_VesselIdAndEndDate(String vesselId, LocalDate endDate);
    List<ProjectPlanEntity> findByVessel_VesselIdAndStatus(String vesselId, Integer status);
    List<ProjectPlanEntity> findByStartDateAndEndDate(LocalDate startDate, LocalDate endDate);
    List<ProjectPlanEntity> findByStartDateAndStatus(LocalDate startDate, Integer status);
    List<ProjectPlanEntity> findByEndDateAndStatus(LocalDate endDate, Integer status);

    // 3개 조건
    List<ProjectPlanEntity> findByProject_ProjectIdAndVessel_VesselIdAndStartDate(String projectId, String vesselId, LocalDate startDate);
    List<ProjectPlanEntity> findByProject_ProjectIdAndVessel_VesselIdAndEndDate(String projectId, String vesselId, LocalDate endDate);
    List<ProjectPlanEntity> findByProject_ProjectIdAndVessel_VesselIdAndStatus(String projectId, String vesselId, Integer status);
    List<ProjectPlanEntity> findByProject_ProjectIdAndStartDateAndEndDate(String projectId, LocalDate startDate, LocalDate endDate);
    List<ProjectPlanEntity> findByProject_ProjectIdAndStartDateAndStatus(String projectId, LocalDate startDate, Integer status);
    List<ProjectPlanEntity> findByProject_ProjectIdAndEndDateAndStatus(String projectId, LocalDate endDate, Integer status);
    List<ProjectPlanEntity> findByVessel_VesselIdAndStartDateAndEndDate(String vesselId, LocalDate startDate, LocalDate endDate);
    List<ProjectPlanEntity> findByVessel_VesselIdAndStartDateAndStatus(String vesselId, LocalDate startDate, Integer status);
    List<ProjectPlanEntity> findByVessel_VesselIdAndEndDateAndStatus(String vesselId, LocalDate endDate, Integer status);
    List<ProjectPlanEntity> findByStartDateAndEndDateAndStatus(LocalDate startDate, LocalDate endDate, Integer status);

    // 4개 조건
    List<ProjectPlanEntity> findByProject_ProjectIdAndVessel_VesselIdAndStartDateAndEndDate(String projectId, String vesselId, LocalDate startDate, LocalDate endDate);
    List<ProjectPlanEntity> findByProject_ProjectIdAndVessel_VesselIdAndStartDateAndStatus(String projectId, String vesselId, LocalDate startDate, Integer status);
    List<ProjectPlanEntity> findByProject_ProjectIdAndVessel_VesselIdAndEndDateAndStatus(String projectId, String vesselId, LocalDate endDate, Integer status);
    List<ProjectPlanEntity> findByProject_ProjectIdAndStartDateAndEndDateAndStatus(String projectId, LocalDate startDate, LocalDate endDate, Integer status);
    List<ProjectPlanEntity> findByVessel_VesselIdAndStartDateAndEndDateAndStatus(String vesselId, LocalDate startDate, LocalDate endDate, Integer status);

    // 5개 조건
    List<ProjectPlanEntity> findByProject_ProjectIdAndVessel_VesselIdAndStartDateAndEndDateAndStatus(String projectId, String vesselId, LocalDate startDate, LocalDate endDate, Integer status);
}
