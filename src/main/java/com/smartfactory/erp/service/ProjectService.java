package com.smartfactory.erp.service;

import com.smartfactory.erp.dto.CustomerDto;
import com.smartfactory.erp.dto.ProjectDto;
import com.smartfactory.erp.entity.CustomerEntity;
import com.smartfactory.erp.entity.ProjectEntity;
import com.smartfactory.erp.repository.ProjectRepository;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
    public ProjectDto searchByProject(String projectId) {
        return projectRepository.findById(projectId)
                .map(ProjectDto::fromEntity)
                .orElse(null); // 데이터가 없으면 null을 반환
    }

    public List<ProjectDto> searchByProjectNm(String projectNm) {
        return projectRepository.findByProjectNmContaining(projectNm).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    public List<ProjectDto> searchByCustomerId(String customerId) {
        return projectRepository.findByCustomerId(customerId).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    public List<ProjectDto> searchByDateRange(LocalDate startDate, LocalDate deliveryDate) {
        return projectRepository.findByStartDateBetween(startDate, deliveryDate).stream()
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
        return projectRepository.findByProjectIdAndCustomerId(projectId, customerId).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    public List<ProjectDto> searchByProjectIdAndDateRange(String projectId, LocalDate startDate, LocalDate deliveryDate) {
        return projectRepository.findByProjectIdAndStartDateBetween(projectId, startDate, deliveryDate).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    public List<ProjectDto> searchByProjectNmAndCustomerId(String projectNm, String customerId) {
        return projectRepository.findByProjectNmContainingAndCustomerId(projectNm, customerId).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    public List<ProjectDto> searchByProjectNmAndDateRange(String projectNm, LocalDate startDate, LocalDate deliveryDate) {
        return projectRepository.findByProjectNmContainingAndStartDateBetween(projectNm, startDate, deliveryDate).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    public List<ProjectDto> searchByCustomerIdAndDateRange(String customerId, LocalDate startDate, LocalDate deliveryDate) {
        return projectRepository.findByCustomerIdAndStartDateBetween(customerId, startDate, deliveryDate).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    // --- 조건이 3개인 경우 ---
    public List<ProjectDto> searchByProjectIdAndProjectNmAndCustomerId(String projectId, String projectNm, String customerId) {
        return projectRepository.findByProjectIdAndProjectNmContainingAndCustomerId(projectId, projectNm, customerId).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    public List<ProjectDto> searchByProjectIdAndProjectNmAndDateRange(String projectId, String projectNm, LocalDate startDate, LocalDate deliveryDate) {
        return projectRepository.findByProjectIdAndProjectNmContainingAndStartDateBetween(projectId, projectNm, startDate, deliveryDate).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    public List<ProjectDto> searchByProjectIdAndCustomerIdAndDateRange(String projectId, String customerId, LocalDate startDate, LocalDate deliveryDate) {
        return projectRepository.findByProjectIdAndCustomerIdAndStartDateBetween(projectId, customerId, startDate, deliveryDate).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    public List<ProjectDto> searchByProjectNmAndCustomerIdAndDateRange(String projectNm, String customerId, LocalDate startDate, LocalDate deliveryDate) {
        return projectRepository.findByProjectNmContainingAndCustomerIdAndStartDateBetween(projectNm, customerId, startDate, deliveryDate).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }

    // --- 조건이 4개인 경우 ---
    public List<ProjectDto> searchByAllConditions(String projectId, String projectNm, String customerId, LocalDate startDate, LocalDate deliveryDate) {
        return projectRepository.findByProjectIdAndProjectNmContainingAndCustomerIdAndStartDateBetween(projectId, projectNm, customerId, startDate, deliveryDate).stream()
                .map(ProjectDto::fromEntity)
                .toList();
    }
}
