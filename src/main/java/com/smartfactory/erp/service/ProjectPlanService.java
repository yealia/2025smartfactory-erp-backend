package com.smartfactory.erp.service;

import com.smartfactory.erp.dto.ProjectPlanDto;
import com.smartfactory.erp.entity.ProjectEntity;
import com.smartfactory.erp.entity.ProjectPlanEntity;
import com.smartfactory.erp.entity.VesselEntity;
import com.smartfactory.erp.repository.ProjectPlanRepository;
import com.smartfactory.erp.repository.ProjectRepository;
import com.smartfactory.erp.repository.VesselRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectPlanService { // 인터페이스가 아닌 클래스로 직접 정의

    private final ProjectPlanRepository projectPlanRepository;
    private final ProjectRepository projectRepository;
    private final VesselRepository vesselRepository;

    @Transactional
    public ProjectPlanDto createPlan(ProjectPlanDto dto) {
        ProjectEntity project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + dto.getProjectId()));
        VesselEntity vessel = vesselRepository.findById(dto.getVesselId())
                .orElseThrow(() -> new EntityNotFoundException("Vessel not found with id: " + dto.getVesselId()));

        ProjectPlanEntity entity = dto.toEntity();

        // ================== ID 생성 로직 수정 ==================
        // 1. 연도를 기반으로 ID 접두사 생성 (예: "PP-2025-")
        String yearPrefix = "PP-" + LocalDate.now().getYear() + "-";

        // 2. DB에서 해당 연도의 마지막 plan ID를 조회
        Optional<ProjectPlanEntity> lastPlanOpt = projectPlanRepository.findTopByPlanIdStartingWithOrderByPlanIdDesc(yearPrefix);

        int nextSeq = 1; // 기본 순번은 1로 시작
        if (lastPlanOpt.isPresent()) {
            // 3. 마지막 ID가 있다면, 순번을 추출하여 1을 더함
            String lastPlanId = lastPlanOpt.get().getPlanId(); // 예: "PP-2025-004"
            String seqStr = lastPlanId.substring(yearPrefix.length()); // "004"
            nextSeq = Integer.parseInt(seqStr) + 1; // 4 + 1 = 5
        }

        // 4. 새로운 순번을 3자리 문자열로 포맷팅 (예: 5 -> "005")
        String newPlanId = yearPrefix + String.format("%03d", nextSeq);

        // 5. 생성된 ID를 Entity에 설정
        entity.setPlanId(newPlanId);

        entity.setProject(project);
        entity.setVessel(vessel);

        ProjectPlanEntity savedEntity = projectPlanRepository.save(entity);
        return ProjectPlanDto.fromEntity(savedEntity);
    }

    @Transactional
    public ProjectPlanDto getPlanById(String planId) {
        return projectPlanRepository.findById(planId)
                .map(ProjectPlanDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Project Plan not found with id: " + planId));
    }

    @Transactional
    public ProjectPlanDto updatePlan(String planId, ProjectPlanDto dto) {
        ProjectPlanEntity existingEntity = projectPlanRepository.findById(planId)
                .orElseThrow(() -> new EntityNotFoundException("Project Plan not found with id: " + planId));

        existingEntity.setPlanScope(dto.getPlanScope());
        existingEntity.setStartDate(dto.getStartDate());
        existingEntity.setEndDate(dto.getEndDate());
        existingEntity.setProgressRate(dto.getProgressRate());
        existingEntity.setStatus(dto.getStatus());
        existingEntity.setRemark(dto.getRemark());

        return ProjectPlanDto.fromEntity(existingEntity);
    }

    @Transactional
    public void deletePlan(String planId) {
        if (!projectPlanRepository.existsById(planId)) {
            throw new EntityNotFoundException("Project Plan not found with id: " + planId);
        }
        projectPlanRepository.deleteById(planId);
    }

    @Transactional
    public List<ProjectPlanDto> search(String projectId, String vesselId, LocalDate startDate, LocalDate endDate, Integer status) {
        Specification<ProjectPlanEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (projectId != null && !projectId.isEmpty()) {
                predicates.add(cb.equal(root.get("project").get("projectId"), projectId));
            }
            if (vesselId != null && !vesselId.isEmpty()) {
                predicates.add(cb.equal(root.get("vessel").get("vesselId"), vesselId));
            }
            if (startDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("startDate"), startDate));
            }
            if (endDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("endDate"), endDate));
            }
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return projectPlanRepository.findAll(spec).stream()
                .map(ProjectPlanDto::fromEntity)
                .collect(Collectors.toList());
    }
}