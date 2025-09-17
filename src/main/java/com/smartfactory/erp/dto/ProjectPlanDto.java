package com.smartfactory.erp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartfactory.erp.entity.ProjectPlanEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ProjectPlanDto {
    private String planId;          // 계획 ID
    private String projectId;       // 프로젝트 ID
    private String vesselId;        // 선박 ID
    private String planScope;       // 계획 범위
    private LocalDate startDate;    // 시작일
    private LocalDate endDate;      // 종료일
    private BigDecimal progressRate;// 진행률
    private Integer status;         // 상태
    private String remark;          // 비고
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;  // 생성일
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;  // 수정일

    /**
     * DTO를 Entity로 변환하는 메서드 (데이터 저장/수정 시 사용)
     */
    public ProjectPlanEntity toEntity() {
        ProjectPlanEntity entity = new ProjectPlanEntity();
        entity.setPlanId(this.planId);
        entity.setProjectId(this.projectId);
        entity.setVesselId(this.vesselId);
        entity.setPlanScope(this.planScope);
        entity.setStartDate(this.startDate);
        entity.setEndDate(this.endDate);
        entity.setProgressRate(this.progressRate);
        entity.setStatus(this.status);
        entity.setRemark(this.remark);
        return entity;
    }

    /**
     * Entity를 DTO로 변환하는 정적 메서드 (데이터 조회 시 사용)
     */
    public static ProjectPlanDto fromEntity(ProjectPlanEntity entity) {
        ProjectPlanDto dto = new ProjectPlanDto();
        dto.setPlanId(entity.getPlanId());
        dto.setProjectId(entity.getProjectId());
        dto.setVesselId(entity.getVesselId());
        dto.setPlanScope(entity.getPlanScope());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setProgressRate(entity.getProgressRate());
        dto.setStatus(entity.getStatus());
        dto.setRemark(entity.getRemark());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}
