package com.smartfactory.erp.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "projects")
public class ProjectEntity {

    @Id
    @Column(name = "project_id", nullable = false, length = 20)
    private String projectId;

    @Column(name = "project_nm", nullable = false, length = 50)
    private String projectNm;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @Column(name = "actual_delivery_date")
    private LocalDate actualDeliveryDate;

    @Column(name = "total_budget", precision = 20, scale = 2)
    private BigDecimal totalBudget;

    @Column(name = "execution_budget", precision = 20, scale = 2)
    private BigDecimal executionBudget;

    @Column(name = "currency_code", nullable = false, length = 3)
    private String currencyCode = "KRW";

    @Column(name = "progress_rate", nullable = false, precision = 5, scale = 2)
    private BigDecimal progressRate;

    @Column(name = "priority", nullable = false)
    private Integer priority;

    @Column(name = "remark", length = 255)
    private String remark;

    @Column(name = "created_at", updatable = false, insertable = false,
            columnDefinition = "datetime default current_timestamp")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false,
            columnDefinition = "datetime on update current_timestamp")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;

    @OneToMany(mappedBy = "project")
    private List<VesselEntity> vessels;

    @OneToMany(mappedBy = "project")
    private List<ProjectPlanEntity> projectPlans;
}