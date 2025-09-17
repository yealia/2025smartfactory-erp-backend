package com.smartfactory.erp.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "movements")
public class MovementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movement_id", nullable = false)
    private Integer movementId;

    @Column(name = "occurred_at", nullable = false)
    private LocalDateTime occurredAt;

    @Column(name = "material_id", nullable = false)
    private Integer materialId;

    @Column(name = "qty", nullable = false)
    private Integer qty;

    @Column(name = "warehouse_from", length = 20)
    private String warehouseFrom;

    @Column(name = "warehouse_to", length = 20)
    private String warehouseTo;

    @Column(name = "location_from", length = 50)
    private String locationFrom;

    @Column(name = "location_to", length = 50)
    private String locationTo;

    @Column(name = "movement_type", nullable = false, length = 30)
    private String movementType;

    @Column(name = "source_type", nullable = false, length = 30)
    private String sourceType;

    @Column(name = "purchase_order_id", nullable = false, length = 50)
    private String purchaseOrderId;

    @Column(name = "order_detail_id", nullable = false)
    private Integer orderDetailId;

    @Column(name = "qc_id", nullable = false)
    private Integer qcId;

    @Column(name = "user_id", nullable = false, length = 50)
    private String userId;

    @Column(name = "remark", length = 255)
    private String remark;

    @Column(name = "created_at", updatable = false, insertable = false,
            columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "idempotency_key", nullable = false, unique = true, length = 100)
    private String idempotencyKey;

    @Column(name = "work_order_id")
    private Integer workOrderId;
}