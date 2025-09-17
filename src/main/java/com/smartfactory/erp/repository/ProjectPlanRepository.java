package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.ProjectPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProjectPlanRepository extends JpaRepository<ProjectPlanEntity, String> {

// ## 조건이 1개인 경우 (4가지) ##
    List<ProjectPlanEntity> findByProjectId(String projectId);
    List<ProjectPlanEntity> findByVesselId(String vesselId);
    List<ProjectPlanEntity> findByStartDateBetween(LocalDate startDate, LocalDate endDate);
    List<ProjectPlanEntity> findByStatus(Integer status);

// ## 조건이 2개인 경우 (6가지) ##
    List<ProjectPlanEntity> findByProjectIdAndVesselId(String projectId, String vesselId);
    List<ProjectPlanEntity> findByProjectIdAndStartDateBetween(String projectId, LocalDate startDate, LocalDate endDate);
    List<ProjectPlanEntity> findByProjectIdAndStatus(String projectId, Integer status);
    List<ProjectPlanEntity> findByVesselIdAndStartDateBetween(String vesselId, LocalDate startDate, LocalDate endDate);
    List<ProjectPlanEntity> findByVesselIdAndStatus(String vesselId, Integer status);
    List<ProjectPlanEntity> findByStartDateBetweenAndStatus(LocalDate startDate, LocalDate endDate, Integer status);

// ## 조건이 3개인 경우 (4가지) ##

    List<ProjectPlanEntity> findByProjectIdAndVesselIdAndStartDateBetween(String projectId, String vesselId, LocalDate startDate, LocalDate endDate);
    List<ProjectPlanEntity> findByProjectIdAndVesselIdAndStatus(String projectId, String vesselId, Integer status);
    List<ProjectPlanEntity> findByProjectIdAndStartDateBetweenAndStatus(String projectId, LocalDate startDate, LocalDate endDate, Integer status);
    List<ProjectPlanEntity> findByVesselIdAndStartDateBetweenAndStatus(String vesselId, LocalDate startDate, LocalDate endDate, Integer status);

// ## 조건이 4개인 경우 (1가지) ##
    List<ProjectPlanEntity> findByProjectIdAndVesselIdAndStartDateBetweenAndStatus(String projectId, String vesselId, LocalDate startDate, LocalDate endDate, Integer status);
}