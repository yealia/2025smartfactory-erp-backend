package com.smartfactory.erp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name="vessels")
public class VesselEntity {
    @Id
    @Column(name = "vessel_id", length = 20, nullable = false)
    private String vesselId; // 선박ID (PK)

    @Column(name = "vessel_nm", length = 50, nullable = false)
    private String vesselNm; // 선박명

    @Column(name = "vessel_type", length = 20, nullable = false)
    private String vesselType; // 선박유형 (컨테이너선/벌크선 등)

    @Column(name = "vessel_length", precision = 6, scale = 2)
    private BigDecimal vesselLength; // 길이 (m)

    @Column(name = "vessel_beam", precision = 5, scale = 2)
    private BigDecimal vesselBeam; // 폭 (m)

    @Column(name = "cargo_capacity", length = 20)
    private String cargoCapacity; // 적재능력 (TEU, DWT 등)

    @Column(name = "engine_spec", length = 100)
    private String engineSpec; // 엔진 스펙

    @Column(name = "total_weight", precision = 10, scale = 2)
    private BigDecimal totalWeight; // 총중량
}
