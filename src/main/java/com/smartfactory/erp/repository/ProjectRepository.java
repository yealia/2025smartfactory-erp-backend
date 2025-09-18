package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, String> {

    // 1개 조건 (5개)
    List<ProjectEntity> findByProjectId(String projectId);
    List<ProjectEntity> findByProjectNmContaining(String projectNm);
    List<ProjectEntity> findByCustomer_CustomerId(String customerId);
    List<ProjectEntity> findByStartDate(LocalDate startDate);
    List<ProjectEntity> findByDeliveryDate(LocalDate deliveryDate);

    // 2개 조건 (10개)
    List<ProjectEntity> findByProjectIdAndProjectNmContaining(String projectId, String projectNm);
    List<ProjectEntity> findByProjectIdAndCustomer_CustomerId(String projectId, String customerId);
    List<ProjectEntity> findByProjectIdAndStartDate(String projectId, LocalDate startDate);
    List<ProjectEntity> findByProjectIdAndDeliveryDate(String projectId, LocalDate deliveryDate);
    List<ProjectEntity> findByProjectNmContainingAndCustomer_CustomerId(String projectNm, String customerId);
    List<ProjectEntity> findByProjectNmContainingAndStartDate(String projectNm, LocalDate startDate);
    List<ProjectEntity> findByProjectNmContainingAndDeliveryDate(String projectNm, LocalDate deliveryDate);
    List<ProjectEntity> findByCustomer_CustomerIdAndStartDate(String customerId, LocalDate startDate);
    List<ProjectEntity> findByCustomer_CustomerIdAndDeliveryDate(String customerId, LocalDate deliveryDate);
    List<ProjectEntity> findByStartDateAndDeliveryDate(LocalDate startDate, LocalDate deliveryDate);

    // 3개 조건 (10개)
    List<ProjectEntity> findByProjectIdAndProjectNmContainingAndCustomer_CustomerId(String projectId, String projectNm, String customerId);
    List<ProjectEntity> findByProjectIdAndProjectNmContainingAndStartDate(String projectId, String projectNm, LocalDate startDate);
    List<ProjectEntity> findByProjectIdAndProjectNmContainingAndDeliveryDate(String projectId, String projectNm, LocalDate deliveryDate);
    List<ProjectEntity> findByProjectIdAndCustomer_CustomerIdAndStartDate(String projectId, String customerId, LocalDate startDate);
    List<ProjectEntity> findByProjectIdAndCustomer_CustomerIdAndDeliveryDate(String projectId, String customerId, LocalDate deliveryDate);
    List<ProjectEntity> findByProjectIdAndStartDateAndDeliveryDate(String projectId, LocalDate startDate, LocalDate deliveryDate);
    List<ProjectEntity> findByProjectNmContainingAndCustomer_CustomerIdAndStartDate(String projectNm, String customerId, LocalDate startDate);
    List<ProjectEntity> findByProjectNmContainingAndCustomer_CustomerIdAndDeliveryDate(String projectNm, String customerId, LocalDate deliveryDate);
    List<ProjectEntity> findByProjectNmContainingAndStartDateAndDeliveryDate(String projectNm, LocalDate startDate, LocalDate deliveryDate);
    List<ProjectEntity> findByCustomer_CustomerIdAndStartDateAndDeliveryDate(String customerId, LocalDate startDate, LocalDate deliveryDate);

    // 4개 조건 (5개)
    List<ProjectEntity> findByProjectIdAndProjectNmContainingAndCustomer_CustomerIdAndStartDate(String projectId, String projectNm, String customerId, LocalDate startDate);
    List<ProjectEntity> findByProjectIdAndProjectNmContainingAndCustomer_CustomerIdAndDeliveryDate(String projectId, String projectNm, String customerId, LocalDate deliveryDate);
    List<ProjectEntity> findByProjectIdAndProjectNmContainingAndStartDateAndDeliveryDate(String projectId, String projectNm, LocalDate startDate, LocalDate deliveryDate);
    List<ProjectEntity> findByProjectIdAndCustomer_CustomerIdAndStartDateAndDeliveryDate(String projectId, String customerId, LocalDate startDate, LocalDate deliveryDate);
    List<ProjectEntity> findByProjectNmContainingAndCustomer_CustomerIdAndStartDateAndDeliveryDate(String projectNm, String customerId, LocalDate startDate, LocalDate deliveryDate);

    // 5개 조건 (1개)
    List<ProjectEntity> findByProjectIdAndProjectNmContainingAndCustomer_CustomerIdAndStartDateAndDeliveryDate(
            String projectId, String projectNm, String customerId, LocalDate startDate, LocalDate deliveryDate
    );
}
