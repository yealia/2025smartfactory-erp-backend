package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, String> {

    // 1. 프로젝트명으로만 검색 (Containing: 부분 일치)
    List<ProjectEntity> findByProjectNmContaining(String projectNm);

    // 2. 고객 ID로만 검색
    List<ProjectEntity> findByCustomerId(String customerId);

    // 3. 기간으로만 검색
    List<ProjectEntity> findByStartDateBetween(LocalDate startDate, LocalDate deliveryDate);

    // ## 조건이 2개인 경우 (6가지)

    // 4. 프로젝트 ID + 프로젝트명
    List<ProjectEntity> findByProjectIdAndProjectNmContaining(String projectId, String projectNm);

    // 5. 프로젝트 ID + 고객 ID
    List<ProjectEntity> findByProjectIdAndCustomerId(String projectId, String customerId);

    // 6. 프로젝트 ID + 기간
    List<ProjectEntity> findByProjectIdAndStartDateBetween(String projectId, LocalDate startDate, LocalDate deliveryDate);

    // 7. 프로젝트명 + 고객 ID
    List<ProjectEntity> findByProjectNmContainingAndCustomerId(String projectNm, String customerId);

    // 8. 프로젝트명 + 기간
    List<ProjectEntity> findByProjectNmContainingAndStartDateBetween(String projectNm, LocalDate startDate, LocalDate deliveryDate);

    // 9. 고객 ID + 기간
    List<ProjectEntity> findByCustomerIdAndStartDateBetween(String customerId, LocalDate startDate, LocalDate deliveryDate);

    // ## 조건이 3개인 경우 (4가지)

    // 10. 프로젝트 ID + 프로젝트명 + 고객 ID
    List<ProjectEntity> findByProjectIdAndProjectNmContainingAndCustomerId(String projectId, String projectNm, String customerId);

    // 11. 프로젝트 ID + 프로젝트명 + 기간
    List<ProjectEntity> findByProjectIdAndProjectNmContainingAndStartDateBetween(String projectId, String projectNm, LocalDate startDate, LocalDate deliveryDate);

    // 12. 프로젝트 ID + 고객 ID + 기간
    List<ProjectEntity> findByProjectIdAndCustomerIdAndStartDateBetween(String projectId, String customerId, LocalDate startDate, LocalDate deliveryDate);

    // 13. 프로젝트명 + 고객 ID + 기간
    List<ProjectEntity> findByProjectNmContainingAndCustomerIdAndStartDateBetween(String projectNm, String customerId, LocalDate startDate, LocalDate deliveryDate);

    // 14. 프로젝트ID / 프로젝트명 / 고객ID / 시작일~종료일
    List<ProjectEntity> findByProjectIdAndProjectNmContainingAndCustomerIdAndStartDateBetween(String projectId, String projectNm, String customerId, LocalDate startDate, LocalDate deliveryDate);
}


