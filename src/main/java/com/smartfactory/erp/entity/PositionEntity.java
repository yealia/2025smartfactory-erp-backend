package com.smartfactory.erp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "positions")
public class PositionEntity {

    @Id
    @Column(name = "position_id", nullable = false)
    private Integer positionId;

    @Column(name = "position_nm", nullable = false, length = 20)
    private String positionNm;

    @Column(name = "department_id", nullable = false)
    private Integer departmentId;

    @Column(name = "job_description", length = 50)
    private String jobDescription;

    @Column(name = "position_type", length = 10)
    private String positionType;

    @Column(name = "created_at", updatable = false, insertable = false,
            columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false,
            columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", insertable = false, updatable = false)
    private DepartmentEntity department;

    @OneToMany(mappedBy = "position", fetch = FetchType.LAZY)
    private List<EmployeeEntity> employees;

}
