package com.smartfactory.erp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "materials")
public class MaterialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    @Column(name = "material_id", nullable = false)
    private Integer materialId;   // 자재 ID (PK)

    @Column(name = "material_nm", length = 50, nullable = false)
    private String materialNm;    // 자재명

    @Column(name = "category", length = 20)
    private String category;      // 자재분류

    @Column(name = "contract_date")
    private LocalDate contractDate;

    @Column(name = "specification", length = 200, nullable = false)
    private String specification; // 규격

    @Column(name = "unit", length = 20, nullable = false)
    private String unit;          // 단위 (EA, KG 등)

    @Column(name = "unit_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal unitPrice; // 기준단가

    @Column(name = "current_price", precision = 10, scale = 2)
    private BigDecimal currentPrice; // 현재단가

    @Column(name = "min_stock_quantity", nullable = false)
    private Integer minStockQuantity; // 최소재고

    @Column(name = "max_stock_quantity", nullable = false)
    private Integer maxStockQuantity; // 최대재고

    @Column(name = "current_stock")
    private Integer currentStock; // 현재고

    @Column(name = "lead_time")
    private Integer leadTime; // 리드타임(일)

    // 공급업체 FK 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", referencedColumnName = "supplier_id")
    private SupplierEntity supplier; // 공급업체

    @Column(name = "last_purchase_date")
    private LocalDate lastPurchaseDate; // 최근구매일

    @Column(name = "status", columnDefinition = "int default 0")
    private Integer status; // 0:사용중, 1:단종, 2:대체필요

    @Column(name = "remark", length = 255)
    private String remark; // 비고

    //20250903추가
    @Column(name = "warehouse", length = 20, nullable = false)
    private String warehouse; // 창고

    @Column(name = "location", length = 50, nullable = false)
    private String location; // 위치
}