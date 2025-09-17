package com.smartfactory.erp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartfactory.erp.entity.ProjectEntity;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ProjectDto {
    private String projectId;             // 프로젝트 ID
    private String projectNm;             // 프로젝트명
    private LocalDate startDate;          // 시작일
    private LocalDate deliveryDate;       // 납기일
    private LocalDate actualDeliveryDate; // 실제 납기일
    private BigDecimal totalBudget;       // 총 예산
    private BigDecimal executionBudget;   // 집행 예산
    private String currencyCode;          // 통화 코드
    private BigDecimal progressRate;      // 진행률
    private Integer priority;             // 우선순위
    private String customerId;            // 고객 ID
    private String employeeId;            // 담당자 ID
    private String remark;                // 비고
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;      // 생성일
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;      // 수정일

    public ProjectEntity toEntity() {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setProjectId(this.projectId);
        projectEntity.setProjectNm(this.projectNm);
        projectEntity.setStartDate(this.startDate);
        projectEntity.setDeliveryDate(this.deliveryDate);
        projectEntity.setActualDeliveryDate(this.actualDeliveryDate);
        projectEntity.setTotalBudget(this.totalBudget);
        projectEntity.setExecutionBudget(this.executionBudget);
        projectEntity.setCurrencyCode(this.currencyCode);
        projectEntity.setProgressRate(this.progressRate);
        projectEntity.setPriority(this.priority);
        projectEntity.setCustomerId(this.customerId);
        projectEntity.setEmployeeId(this.employeeId);
        projectEntity.setRemark(this.remark);
        projectEntity.setCreatedAt(this.createdAt);
        projectEntity.setUpdatedAt(this.updatedAt);
        return projectEntity;
    }

    public static ProjectDto fromEntity(ProjectEntity entity){
        ProjectDto dto = new ProjectDto();
        dto.setProjectId(entity.getProjectId());
        dto.setProjectNm(entity.getProjectNm());
        dto.setStartDate(entity.getStartDate());
        dto.setDeliveryDate(entity.getDeliveryDate());
        dto.setActualDeliveryDate(entity.getActualDeliveryDate());
        dto.setTotalBudget(entity.getTotalBudget());
        dto.setExecutionBudget(entity.getExecutionBudget());
        dto.setCurrencyCode(entity.getCurrencyCode());
        dto.setProgressRate(entity.getProgressRate());
        dto.setPriority(entity.getPriority());
        dto.setCustomerId(entity.getCustomerId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setRemark(entity.getRemark());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}


