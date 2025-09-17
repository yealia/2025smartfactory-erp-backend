package com.smartfactory.erp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "vessels")
public class VesselEntity {

    @Id
    @Column(name = "vessel_id", nullable = false, length = 20)
    private String vesselId;

    @Column(name = "vessel_nm", nullable = false, length = 50)
    private String vesselNm;

    @Column(name = "vessel_type", nullable = false, length = 20)
    private String vesselType;

    @Column(name = "status")
    private Integer status;

    @Column(name = "vessel_length", precision = 6, scale = 2)
    private BigDecimal vesselLength;

    @Column(name = "vessel_beam", precision = 5, scale = 2)
    private BigDecimal vesselBeam;

    @Column(name = "vessel_depth", precision = 5, scale = 2)
    private BigDecimal vesselDepth;

    @Column(name = "cargo_capacity", length = 20)
    private String cargoCapacity;

    @Column(name = "engine_spec", length = 100)
    private String engineSpec;

    @Column(name = "total_weight", precision = 10, scale = 2)
    private BigDecimal totalWeight;

    @Column(name = "actual_delivery_date")
    private LocalDate actualDeliveryDate;

    @Column(name = "project_id", nullable = false, length = 20)
    private String projectId;

    @Column(name = "remark", length = 255)
    private String remark;

    @Column(name = "created_at", updatable = false, insertable = false,
            columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false,
            columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;
}