package com.smartfactory.erp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "projects")
public class ProjectEntity {

    @Id
    @Column(name = "project_id", length = 20, nullable = false)
    private String projectId;

    @Column(name = "project_nm", length = 50, nullable = false)
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

    @Column(name = "currency_code", length = 3, nullable = false)
    private String currencyCode = "KRW";

    @Column(name = "progress_rate", precision = 5, scale = 2, nullable = false)
    private BigDecimal progressRate = BigDecimal.valueOf(0);

    @Column(name = "priority", nullable = false)
    private Integer priority;

    @Column(name = "customer_id", length = 20, nullable = false)
    private String customerId;

    @Column(name = "employee_id", length = 20, nullable = false)
    private String employeeId;

    @Column(name = "remark", length = 255)
    private String remark;

    @Column(name = "created_at", updatable = false, insertable = false,
            columnDefinition = "datetime default current_timestamp")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false,
            columnDefinition = "datetime on update current_timestamp")
    private LocalDateTime updatedAt;
}
