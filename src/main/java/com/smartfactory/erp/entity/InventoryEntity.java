package com.smartfactory.erp.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "inventory")
public class InventoryEntity {

    @Id
    @Column(name = "inventory_id", nullable = false, length = 20)
    private String inventoryId;

    // 🔽 DB 수정을 하지 않는 조회용 ID 필드
    @Column(name = "material_id", nullable = false, insertable = false, updatable = false)
    private Integer materialId;

    @Column(name = "warehouse", length = 20)
    private String warehouse;

    @Column(name = "location", nullable = false, length = 50)
    private String location;

    @Column(name = "on_hand", nullable = false)
    private Integer onHand;

    @Column(name = "reserved_qty", nullable = false)
    private Integer reservedQty;

    @Column(name = "safety_stock", nullable = false)
    private Integer safetyStock;

    @Column(name = "reorder_point", nullable = false)
    private Integer reorderPoint;

    @Version // 낙관적 락을 위한 Version 필드 (동시 수정으로 인한 덮어쓰기 문제를 방지)
    @Column(name = "version", nullable = false)
    private Integer version;

    @Column(name = "remark", length = 255)
    private String remark;

    @Column(name = "created_at", updatable = false, insertable = false,
            columnDefinition = "datetime default current_timestamp")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false,
            columnDefinition = "datetime on update current_timestamp")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id")
    private MaterialEntity material;
}