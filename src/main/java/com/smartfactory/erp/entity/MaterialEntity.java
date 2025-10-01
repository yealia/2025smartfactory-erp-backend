package com.smartfactory.erp.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "materials")
public class MaterialEntity {

    @Id
    @Column(name = "material_id", nullable = false)
    private Integer materialId;

    @Column(name = "material_nm", nullable = false, length = 50)
    private String materialNm;

    @Column(name = "category", length = 20)
    private String category;

    @Column(name = "specification", nullable = false, length = 200)
    private String specification;

    @Column(name = "unit", nullable = false, length = 20)
    private String unit;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "current_price", precision = 10, scale = 2)
    private BigDecimal currentPrice;

    @Column(name = "min_stock_quantity", nullable = false)
    private Integer minStockQuantity;

    @Column(name = "max_stock_quantity", nullable = false)
    private Integer maxStockQuantity;

    @Column(name = "lead_time")
    private Integer leadTime;

    // 🔽 관계의 주인은 SupplierEntity 객체이므로, 이 컬럼은 DB에 값을 쓰지 않도록 설정
    @Column(name = "supplier_id", insertable = false, updatable = false)
    private Integer supplierId;

    @Column(name = "last_purchase_date")
    private LocalDate lastPurchaseDate;

    @Column(name = "status")
    private Integer status = 0; // 0: 계획, 1: 진행, 2: 완료

    @Column(name = "warehouse", nullable = false, length = 20)
    private String warehouse;

    @Column(name = "location", nullable = false, length = 50)
    private String location;

    @Column(name = "remark", length = 255)
    private String remark;

    @Column(name = "created_at", updatable = false, insertable = false,
            columnDefinition = "datetime default current_timestamp")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false,
            columnDefinition = "datetime on update current_timestamp")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id") // DB의 supplier_id 컬럼과 매핑
    private SupplierEntity supplier;

    @OneToMany(mappedBy = "material")
    private List<PurchaseDetailEntity> purchaseOrderDetails;

    @OneToMany(mappedBy = "material")
    private List<InventoryEntity> inventories;

    @OneToMany(mappedBy = "material")
    private List<MovementEntity> movements;
}