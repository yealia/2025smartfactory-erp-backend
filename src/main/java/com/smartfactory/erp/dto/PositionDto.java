

package com.smartfactory.erp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartfactory.erp.entity.CustomerEntity;
import com.smartfactory.erp.entity.PositionEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PositionDto {
    private int positionId;
    private String positionNm;
    private String jobDescription;
    private String positionType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    private Integer departmentId;

    public PositionEntity toEntity(){
        PositionEntity positionEntity = new PositionEntity();
        positionEntity.setPositionId(this.positionId);
        positionEntity.setPositionNm(this.positionNm);
        positionEntity.setJobDescription(this.jobDescription);
        positionEntity.setPositionType(this.positionType);
        positionEntity.setDepartmentId(this.departmentId);
        return positionEntity;
    }

    public static PositionDto fromEntity(PositionEntity entity) {
        PositionDto dto = new PositionDto();
        dto.setPositionId(entity.getPositionId());
        dto.setPositionNm(entity.getPositionNm());
        dto.setJobDescription(entity.getJobDescription());
        dto.setPositionType(entity.getPositionType());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setDepartmentId(entity.getDepartmentId());
        return dto;
    }
}

