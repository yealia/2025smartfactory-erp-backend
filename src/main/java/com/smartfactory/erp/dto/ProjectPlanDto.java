package com.smartfactory.erp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartfactory.erp.entity.ProjectPlanEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ProjectPlanDto {
    private String planId;
    private String projectId;
    private String vesselId;
    private String planScope;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal progressRate;
    private Integer status;
    private String remark;
    private Boolean isFinal;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    public ProjectPlanEntity toEntity() {
        ProjectPlanEntity entity = new ProjectPlanEntity();
        entity.setPlanId(this.planId);
        entity.setPlanScope(this.planScope);
        entity.setStartDate(this.startDate);
        entity.setEndDate(this.endDate);
        entity.setProgressRate(this.progressRate);
        entity.setStatus(this.status);
        entity.setRemark(this.remark);
        entity.setIsFinal(this.isFinal != null ? this.isFinal : false);
        return entity;
    }

    public static ProjectPlanDto fromEntity(ProjectPlanEntity entity) {
        ProjectPlanDto dto = new ProjectPlanDto();
        dto.setPlanId(entity.getPlanId());

        if (entity.getProject() != null) {
            dto.setProjectId(entity.getProject().getProjectId());
        }
        if (entity.getVessel() != null) {
            dto.setVesselId(entity.getVessel().getVesselId());
        }

        dto.setPlanScope(entity.getPlanScope());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setProgressRate(entity.getProgressRate());
        dto.setStatus(entity.getStatus());
        dto.setRemark(entity.getRemark());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setIsFinal(entity.getIsFinal() != null ? entity.getIsFinal() : false);
        return dto;
    }
}
