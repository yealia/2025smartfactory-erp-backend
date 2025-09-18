package com.smartfactory.erp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartfactory.erp.entity.ProjectEntity;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ProjectDto {
    private String projectId;
    private String projectNm;
    private LocalDate startDate;
    private LocalDate deliveryDate;
    private LocalDate actualDeliveryDate;
    private BigDecimal totalBudget;
    private BigDecimal executionBudget;
    private String currencyCode;
    private BigDecimal progressRate;
    private Integer priority;
    private String customerId;
    private String employeeId;
    private String remark;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    // DTO -> Entity 변환 (Service 계층에서 관계 설정 필요)
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
        projectEntity.setRemark(this.remark);
        // customer와 employee는 Service단에서 찾아서 직접 설정
        return projectEntity;
    }

    // Entity -> DTO 변환
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

        if (entity.getCustomer() != null) {
            dto.setCustomerId(entity.getCustomer().getCustomerId());
        }
        if (entity.getEmployee() != null) {
            dto.setEmployeeId(entity.getEmployee().getEmployeeId());
        }

        dto.setRemark(entity.getRemark());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}


