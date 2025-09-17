package com.smartfactory.erp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "departments")
public class DepartmentEntity {

    @Id
    @Column(name = "department_id", nullable = false)
    private Integer departmentId; // PK

    @Column(name = "department_nm", nullable = false, length = 20)
    private String departmentNm;

    @Column(name = "manager_id", length = 20)
    private String managerId;

    @Column(name = "location_id")
    private Integer locationId;

    @Column(name = "location_nm", length = 50)
    private String locationNm;

    @Column(name = "created_at", updatable = false, insertable = false,
            columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false,
            columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<PositionEntity> positions;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<EmployeeEntity> employees;
}
