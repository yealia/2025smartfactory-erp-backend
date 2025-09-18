package com.smartfactory.erp.service;

import com.smartfactory.erp.dto.ProjectDto;
import com.smartfactory.erp.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    // =========================
    // 조건별 검색 메서드
    // =========================

    // 조건이 없을 때 (전체 조회)
    public List<ProjectDto> searchAllProjects() {
        return projectRepository.findAll().stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    // --- 조건이 1개인 경우 ---
    public ProjectDto searchByProjectId(String projectId) {
        return projectRepository.findById(projectId)
                .map(ProjectDto::fromEntity)
                .orElse(null);
    }

    public List<ProjectDto> searchByProjectNm(String projectNm) {
        return projectRepository.findByProjectNmContaining(projectNm).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    public List<ProjectDto> searchByCustomerId(String customerId) {
        return projectRepository.findByCustomer_CustomerId(customerId).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    public List<ProjectDto> searchByStartDate(LocalDate startDate) {
        return projectRepository.findByStartDate(startDate).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    public List<ProjectDto> searchByDeliveryDate(LocalDate deliveryDate) {
        return projectRepository.findByDeliveryDate(deliveryDate).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    // --- 조건이 2개인 경우 ---
    public List<ProjectDto> searchByProjectIdAndProjectNm(String projectId, String projectNm) {
        return projectRepository.findByProjectIdAndProjectNmContaining(projectId, projectNm).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    public List<ProjectDto> searchByProjectIdAndCustomerId(String projectId, String customerId) {
        return projectRepository.findByProjectIdAndCustomer_CustomerId(projectId, customerId).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    public List<ProjectDto> searchByProjectIdAndStartDate(String projectId, LocalDate startDate) {
        return projectRepository.findByProjectIdAndStartDate(projectId, startDate).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    public List<ProjectDto> searchByProjectIdAndDeliveryDate(String projectId, LocalDate deliveryDate) {
        return projectRepository.findByProjectIdAndDeliveryDate(projectId, deliveryDate).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    public List<ProjectDto> searchByProjectNmAndCustomerId(String projectNm, String customerId) {
        return projectRepository.findByProjectNmContainingAndCustomer_CustomerId(projectNm, customerId).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    public List<ProjectDto> searchByProjectNmAndStartDate(String projectNm, LocalDate startDate) {
        return projectRepository.findByProjectNmContainingAndStartDate(projectNm, startDate).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    public List<ProjectDto> searchByProjectNmAndDeliveryDate(String projectNm, LocalDate deliveryDate) {
        return projectRepository.findByProjectNmContainingAndDeliveryDate(projectNm, deliveryDate).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    public List<ProjectDto> searchByCustomerIdAndStartDate(String customerId, LocalDate startDate) {
        return projectRepository.findByCustomer_CustomerIdAndStartDate(customerId, startDate).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    public List<ProjectDto> searchByCustomerIdAndDeliveryDate(String customerId, LocalDate deliveryDate) {
        return projectRepository.findByCustomer_CustomerIdAndDeliveryDate(customerId, deliveryDate).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    public List<ProjectDto> searchByStartDateAndDeliveryDate(LocalDate startDate, LocalDate deliveryDate) {
        return projectRepository.findByStartDateAndDeliveryDate(startDate, deliveryDate).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    // --- 조건이 3개인 경우 ---
    public List<ProjectDto> searchByProjectIdAndProjectNmAndCustomerId(String projectId, String projectNm, String customerId) {
        return projectRepository.findByProjectIdAndProjectNmContainingAndCustomer_CustomerId(projectId, projectNm, customerId).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    public List<ProjectDto> searchByProjectIdAndProjectNmAndStartDate(String projectId, String projectNm, LocalDate startDate) {
        return projectRepository.findByProjectIdAndProjectNmContainingAndStartDate(projectId, projectNm, startDate).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    public List<ProjectDto> searchByProjectIdAndProjectNmAndDeliveryDate(String projectId, String projectNm, LocalDate deliveryDate) {
        return projectRepository.findByProjectIdAndProjectNmContainingAndDeliveryDate(projectId, projectNm, deliveryDate).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    public List<ProjectDto> searchByProjectIdAndCustomerIdAndStartDate(String projectId, String customerId, LocalDate startDate) {
        return projectRepository.findByProjectIdAndCustomer_CustomerIdAndStartDate(projectId, customerId, startDate).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    public List<ProjectDto> searchByProjectIdAndCustomerIdAndDeliveryDate(String projectId, String customerId, LocalDate deliveryDate) {
        return projectRepository.findByProjectIdAndCustomer_CustomerIdAndDeliveryDate(projectId, customerId, deliveryDate).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    public List<ProjectDto> searchByProjectIdAndStartDateAndDeliveryDate(String projectId, LocalDate startDate, LocalDate deliveryDate) {
        return projectRepository.findByProjectIdAndStartDateAndDeliveryDate(projectId, startDate, deliveryDate).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    public List<ProjectDto> searchByProjectNmAndCustomerIdAndStartDate(String projectNm, String customerId, LocalDate startDate) {
        return projectRepository.findByProjectNmContainingAndCustomer_CustomerIdAndStartDate(projectNm, customerId, startDate).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    public List<ProjectDto> searchByProjectNmAndCustomerIdAndDeliveryDate(String projectNm, String customerId, LocalDate deliveryDate) {
        return projectRepository.findByProjectNmContainingAndCustomer_CustomerIdAndDeliveryDate(projectNm, customerId, deliveryDate).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    public List<ProjectDto> searchByProjectNmAndStartDateAndDeliveryDate(String projectNm, LocalDate startDate, LocalDate deliveryDate) {
        return projectRepository.findByProjectNmContainingAndStartDateAndDeliveryDate(projectNm, startDate, deliveryDate).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    public List<ProjectDto> searchByCustomerIdAndStartDateAndDeliveryDate(String customerId, LocalDate startDate, LocalDate deliveryDate) {
        return projectRepository.findByCustomer_CustomerIdAndStartDateAndDeliveryDate(customerId, startDate, deliveryDate).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    // --- 조건이 4개인 경우 ---
    public List<ProjectDto> searchByProjectIdAndProjectNmAndCustomerIdAndStartDate(String projectId, String projectNm, String customerId, LocalDate startDate) {
        return projectRepository.findByProjectIdAndProjectNmContainingAndCustomer_CustomerIdAndStartDate(projectId, projectNm, customerId, startDate).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    public List<ProjectDto> searchByProjectIdAndProjectNmAndCustomerIdAndDeliveryDate(String projectId, String projectNm, String customerId, LocalDate deliveryDate) {
        return projectRepository.findByProjectIdAndProjectNmContainingAndCustomer_CustomerIdAndDeliveryDate(projectId, projectNm, customerId, deliveryDate).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    public List<ProjectDto> searchByProjectIdAndProjectNmAndStartDateAndDeliveryDate(String projectId, String projectNm, LocalDate startDate, LocalDate deliveryDate) {
        return projectRepository.findByProjectIdAndProjectNmContainingAndStartDateAndDeliveryDate(projectId, projectNm, startDate, deliveryDate).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    public List<ProjectDto> searchByProjectIdAndCustomerIdAndStartDateAndDeliveryDate(String projectId, String customerId, LocalDate startDate, LocalDate deliveryDate) {
        return projectRepository.findByProjectIdAndCustomer_CustomerIdAndStartDateAndDeliveryDate(projectId, customerId, startDate, deliveryDate).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    public List<ProjectDto> searchByProjectNmAndCustomerIdAndStartDateAndDeliveryDate(String projectNm, String customerId, LocalDate startDate, LocalDate deliveryDate) {
        return projectRepository.findByProjectNmContainingAndCustomer_CustomerIdAndStartDateAndDeliveryDate(projectNm, customerId, startDate, deliveryDate).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    // --- 조건이 5개인 경우 ---
    public List<ProjectDto> searchByAllConditions(String projectId, String projectNm, String customerId, LocalDate startDate, LocalDate deliveryDate) {
        return projectRepository.findByProjectIdAndProjectNmContainingAndCustomer_CustomerIdAndStartDateAndDeliveryDate(projectId, projectNm, customerId, startDate, deliveryDate).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }
}
