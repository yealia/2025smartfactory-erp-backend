package com.smartfactory.erp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartfactory.erp.entity.CustomerEntity;
import com.smartfactory.erp.entity.DepartmentEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class DepartmentDto {
    private int departmentId;
    private String departmentNm;
    private String managerId;
    private Integer locationId;
    private String locationNm;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;


    public DepartmentEntity toEntity() {
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setDepartmentId(this.departmentId);
        departmentEntity.setDepartmentNm(this.departmentNm);
        departmentEntity.setManagerId(this.managerId);
        departmentEntity.setLocationId(this.locationId);
        departmentEntity.setLocationNm(this.locationNm);
        departmentEntity.setCreatedAt(this.createdAt);
        departmentEntity.setUpdatedAt(this.updatedAt);
        return departmentEntity;
    }


    public static DepartmentDto fromEntity(DepartmentEntity entity) {
        DepartmentDto dto = new DepartmentDto();
        dto.setDepartmentId(entity.getDepartmentId());
        dto.setDepartmentNm(entity.getDepartmentNm());
        dto.setManagerId(entity.getManagerId());
        dto.setLocationId(entity.getLocationId());
        dto.setLocationNm(entity.getLocationNm());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}