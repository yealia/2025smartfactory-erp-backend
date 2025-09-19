package com.smartfactory.erp.service;

import com.smartfactory.erp.dto.ProjectDto;
import com.smartfactory.erp.entity.CustomerEntity;
import com.smartfactory.erp.entity.EmployeeEntity;
import com.smartfactory.erp.entity.ProjectEntity;
import com.smartfactory.erp.repository.CustomerRepository;
import com.smartfactory.erp.repository.EmployeeRepository;
import com.smartfactory.erp.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;

    // =========================
    // CREATE
    // =========================
    @Transactional
    public ProjectDto createProject(ProjectDto dto) {
        ProjectEntity entity = dto.toEntity();

        if (dto.getCustomerId() != null) {
            CustomerEntity customer = customerRepository.findById(dto.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
            entity.setCustomer(customer);
        }

        if (dto.getEmployeeId() != null) {
            EmployeeEntity employee = employeeRepository.findById(dto.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
            entity.setEmployee(employee);
        }

        ProjectEntity saved = projectRepository.save(entity);
        return ProjectDto.fromEntity(saved);
    }

    // =========================
    // READ (단일 조회)
    // =========================
    public ProjectDto getProject(String projectId) {
        ProjectEntity entity = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return ProjectDto.fromEntity(entity);
    }

    // =========================
    // UPDATE
    // =========================
    @Transactional
    public ProjectDto updateProject(ProjectDto dto) {
        ProjectEntity entity = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        entity.setProjectNm(dto.getProjectNm());
        entity.setStartDate(dto.getStartDate());
        entity.setDeliveryDate(dto.getDeliveryDate());
        entity.setActualDeliveryDate(dto.getActualDeliveryDate());
        entity.setTotalBudget(dto.getTotalBudget());
        entity.setExecutionBudget(dto.getExecutionBudget());
        entity.setCurrencyCode(dto.getCurrencyCode());
        entity.setProgressRate(dto.getProgressRate());
        entity.setPriority(dto.getPriority());
        entity.setRemark(dto.getRemark());

        if (dto.getCustomerId() != null) {
            CustomerEntity customer = customerRepository.findById(dto.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
            entity.setCustomer(customer);
        }

        if (dto.getEmployeeId() != null) {
            EmployeeEntity employee = employeeRepository.findById(dto.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
            entity.setEmployee(employee);
        }

        ProjectEntity updated = projectRepository.save(entity);
        return ProjectDto.fromEntity(updated);
    }

    // =========================
    // DELETE
    // =========================
    @Transactional
    public void deleteProject(String projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new RuntimeException("Project not found");
        }
        projectRepository.deleteById(projectId);
    }

    // =========================
    // DYNAMIC SEARCH (DTO 반환)
    // =========================
    public List<ProjectDto> searchProjects(String projectId, String projectNm, String customerId,
                                           LocalDate startDate, LocalDate deliveryDate) {

        Specification<ProjectEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (projectId != null && !projectId.isEmpty()) {
                predicates.add(cb.equal(root.get("projectId"), projectId));
            }

            if (projectNm != null && !projectNm.isEmpty()) {
                predicates.add(cb.like(root.get("projectNm"), "%" + projectNm + "%"));
            }

            if (customerId != null && !customerId.isEmpty()) {
                predicates.add(cb.equal(root.get("customer").get("customerId"), customerId));
            }

            if (startDate != null) {
                predicates.add(cb.equal(root.get("startDate"), startDate));
            }

            if (deliveryDate != null) {
                predicates.add(cb.equal(root.get("deliveryDate"), deliveryDate));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        List<ProjectEntity> list = projectRepository.findAll(spec);
        return list.stream().map(ProjectDto::fromEntity).collect(Collectors.toList());
    }
}
