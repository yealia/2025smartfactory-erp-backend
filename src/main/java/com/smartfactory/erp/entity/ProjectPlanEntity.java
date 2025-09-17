package com.smartfactory.erp.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "project_plans")
public class ProjectPlanEntity {
    @Id
    @Column(name = "plan_id", length = 30, nullable = false)
    private String planId;

    @Column(name = "project_id", length = 20, nullable = false)
    private String projectId;

    @Column(name = "vessel_id", length = 20, nullable = false)
    private String vesselId;

    @Column(name = "plan_scope", length = 30)
    private String planScope;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "progress_rate", precision = 5, scale = 2, nullable = false)
    private BigDecimal progressRate = BigDecimal.ZERO;

    @Column(name = "status")
    private Integer status = 0;

    @Column(name = "remark")
    private String remark;

    @Column(name = "created_at", updatable = false, insertable = false,
            columnDefinition = "datetime default current_timestamp")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false,
            columnDefinition = "datetime on update current_timestamp")
    private LocalDateTime updatedAt;
}
