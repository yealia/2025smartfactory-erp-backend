package com.smartfactory.erp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartfactory.erp.entity.EmployeeEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EmployeeDto {
    private String employeeId;
    private String employeeNm;
    private int departmentId;
    private int positionId;
    private LocalDate hireDate;

    // ✅ [추가] 전화번호 형식 검증 (예: 010-1234-5678)
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$")
    private String phone;

    // ✅ [추가] 이메일 형식 검증
    @Email
    private String email;

    private String employeeStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    // ✅ [추가] 화면 표시에 사용할 부서명과 직책명
    private String departmentNm;
    private String positionNm;

    // DTO -> Entity 변환
    public EmployeeEntity toEntity() {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setEmployeeId(this.employeeId);
        entity.setEmployeeNm(this.employeeNm);
        entity.setDepartmentId(this.departmentId);
        entity.setPositionId(this.positionId);
        entity.setHireDate(this.hireDate);
        entity.setPhone(this.phone);
        entity.setEmail(this.email);
        entity.setEmployeeStatus(this.employeeStatus);
        return entity;
    }

    // Entity -> DTO 변환
    public static EmployeeDto fromEntity(EmployeeEntity entity) {
        EmployeeDto dto = new EmployeeDto();
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setEmployeeNm(entity.getEmployeeNm());
        dto.setDepartmentId(entity.getDepartmentId());
        dto.setPositionId(entity.getPositionId());
        dto.setHireDate(entity.getHireDate());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setEmployeeStatus(entity.getEmployeeStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());


        // ✅ [수정] 연관된 Entity가 null이 아닐 때만 이름을 가져오도록 처리
        if (entity.getDepartment() != null) {
            dto.setDepartmentNm(entity.getDepartment().getDepartmentNm());
        }
        if (entity.getPosition() != null) {
            dto.setPositionNm(entity.getPosition().getPositionNm());
        }

        return dto;
    }
}
