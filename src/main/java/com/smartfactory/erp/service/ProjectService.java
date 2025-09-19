package com.smartfactory.erp.service;

import com.smartfactory.erp.dto.ProjectDto;
import com.smartfactory.erp.entity.CustomerEntity;
import com.smartfactory.erp.entity.EmployeeEntity;
import com.smartfactory.erp.entity.ProjectEntity;
import com.smartfactory.erp.repository.CustomerRepository;
import com.smartfactory.erp.repository.EmployeeRepository;
import com.smartfactory.erp.repository.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final CustomerRepository customerRepository; // 고객 정보 조회를 위해 주입
    private final EmployeeRepository employeeRepository; // 담당자 정보 조회를 위해 주입

    /**
     * 여러 검색 조건을 받아 동적으로 프로젝트 목록을 검색합니다.
     */
    public List<ProjectDto> searchProjects(String projectId, String projectNm, String customerId, LocalDate startDate, LocalDate deliveryDate) {
        // 1. 검색 조건으로 Specification 객체 생성
        Specification<ProjectEntity> spec = createSpecification(projectId, projectNm, customerId, startDate, deliveryDate);

        // 2. Specification을 사용하여 데이터 조회
        List<ProjectEntity> entities = projectRepository.findAll(spec);

        // 3. 조회된 Entity 목록을 DTO 목록으로 변환하여 반환
        return entities.stream()
                .map(ProjectDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * ID로 단일 프로젝트를 조회합니다.
     */
    public ProjectDto findProjectById(String projectId) {
        ProjectEntity entity = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("프로젝트를 찾을 수 없습니다. ID: " + projectId));
        return ProjectDto.fromEntity(entity);
    }

    /**
     * 새로운 프로젝트를 생성합니다.
     */
    @Transactional
    public ProjectDto createProject(ProjectDto projectDto) {
        // DTO의 toEntity() 메소드로 기본 엔티티 생성
        ProjectEntity newProject = projectDto.toEntity();

        // DTO에 포함된 ID를 사용하여 연관 엔티티(Customer, Employee)를 조회
        CustomerEntity customer = customerRepository.findById(projectDto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("고객을 찾을 수 없습니다. ID: " + projectDto.getCustomerId()));
        EmployeeEntity employee = employeeRepository.findById(projectDto.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("담당자를 찾을 수 없습니다. ID: " + projectDto.getEmployeeId()));

        // 조회한 엔티티로 연관관계(FK) 설정
        newProject.setCustomer(customer);
        newProject.setEmployee(employee);

        // 레파지토리를 통해 저장 후 DTO로 변환하여 반환
        ProjectEntity savedProject = projectRepository.save(newProject);
        return ProjectDto.fromEntity(savedProject);
    }

    /**
     * 기존 프로젝트 정보를 수정합니다.
     */
    @Transactional
    public ProjectDto updateProject(String projectId, ProjectDto projectDto) {
        // 1. DB에서 수정할 기존 엔티티를 조회
        ProjectEntity existingProject = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("수정할 프로젝트를 찾을 수 없습니다. ID: " + projectId));

        // 2. 연관 엔티티들도 새로 조회
        CustomerEntity customer = customerRepository.findById(projectDto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("고객을 찾을 수 없습니다. ID: " + projectDto.getCustomerId()));
        EmployeeEntity employee = employeeRepository.findById(projectDto.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("담당자를 찾을 수 없습니다. ID: " + projectDto.getEmployeeId()));

        // 3. DTO의 내용으로 기존 엔티티의 값을 변경
        existingProject.setProjectNm(projectDto.getProjectNm());
        existingProject.setStartDate(projectDto.getStartDate());
        existingProject.setDeliveryDate(projectDto.getDeliveryDate());
        existingProject.setActualDeliveryDate(projectDto.getActualDeliveryDate());
        existingProject.setTotalBudget(projectDto.getTotalBudget());
        existingProject.setExecutionBudget(projectDto.getExecutionBudget());
        existingProject.setCurrencyCode(projectDto.getCurrencyCode());
        existingProject.setProgressRate(projectDto.getProgressRate());
        existingProject.setPriority(projectDto.getPriority());
        existingProject.setRemark(projectDto.getRemark());
        existingProject.setCustomer(customer);
        existingProject.setEmployee(employee);

        // 4. 트랜잭션이 종료될 때 JPA의 'Dirty Checking'에 의해 자동으로 UPDATE 쿼리가 실행됨
        return ProjectDto.fromEntity(existingProject);
    }

    /**
     * ID로 프로젝트를 삭제합니다.
     */
    @Transactional
    public void deleteProject(String projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new EntityNotFoundException("삭제할 프로젝트를 찾을 수 없습니다. ID: " + projectId);
        }
        projectRepository.deleteById(projectId);
    }

    /**
     * 검색 파라미터를 기반으로 Specification (동적 쿼리 조건) 객체를 생성하는 헬퍼 메소드
     */
    private Specification<ProjectEntity> createSpecification(String projectId, String projectNm, String customerId, LocalDate startDate, LocalDate deliveryDate) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(projectId)) {
                predicates.add(criteriaBuilder.like(root.get("projectId"), "%" + projectId + "%"));
            }
            if (StringUtils.hasText(projectNm)) {
                predicates.add(criteriaBuilder.like(root.get("projectNm"), "%" + projectNm + "%"));
            }
            if (StringUtils.hasText(customerId)) {
                // ProjectEntity와 연관된 CustomerEntity의 customerId 필드로 검색 (Join 발생)
                predicates.add(criteriaBuilder.equal(root.get("customer").get("customerId"), customerId));
            }
            if (startDate != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), startDate));
            }
            if (deliveryDate != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("deliveryDate"), deliveryDate));
            }

            // 생성된 모든 조건을 AND로 연결하여 반환
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}