package com.smartfactory.erp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "employees")
public class EmployeeEntity {

    @Id
    @Column(name = "employee_id", length = 20, nullable = false)
    private String employeeId;

    @Column(name = "employee_nm", length = 10, nullable = false)
    private String employeeNm;

    @Column(name = "department_id", nullable = false)
    private Integer departmentId;

    @Column(name = "position_id", nullable = false)
    private Integer positionId;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "employee_status", length = 10)
    private String employeeStatus;

    @Column(name = "created_at", updatable = false, insertable = false,
            columnDefinition = "datetime default current_timestamp")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false,
            columnDefinition = "datetime on update current_timestamp")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", insertable = false, updatable = false)
    private DepartmentEntity department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", insertable = false, updatable = false)
    private PositionEntity position;
}