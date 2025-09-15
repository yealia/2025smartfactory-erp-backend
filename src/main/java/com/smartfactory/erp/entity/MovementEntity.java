package com.smartfactory.erp.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "inventory_movements")
public class MovementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movement_id")
    private Integer movementId; // PK

    @CreationTimestamp
    @Column(name = "occurred_at", nullable = false, columnDefinition = "datetime default current_timestamp")
    private LocalDateTime occurredAt; // 발생일시

    @Column(name = "material_id", nullable = false)
    private Integer materialId; // 자재ID (FK to materials.material_id)

    @Column(name = "qty", nullable = false)
    private Integer qty; // 수량 (+ 입고, - 반출)

    @Column(name = "warehouse_from", length = 20)
    private String warehouseFrom; // 출고 창고

    @Column(name = "warehouse_to", length = 20)
    private String warehouseTo; // 입고 창고

    @Column(name = "location_from", length = 50)
    private String locationFrom; // 출고 위치

    @Column(name = "location_to", length = 50)
    private String locationTo; // 입고 위치

    @Column(name = "movement_type", length = 30, nullable = false)
    private String movementType; // RECEIPT_QC_PASS, RECEIPT_QC_REJECT, RETURN_TO_SUPPLIER, ...

    @Column(name = "source_type", length = 30, nullable = false)
    private String sourceType; // "QC" 등

    @Column(name = "purchase_order_id", length = 50, nullable = false)
    private String purchaseOrderId;

    @Column(name = "order_detail_id", nullable = false)
    private Integer orderDetailId;

    @Column(name = "qc_id", nullable = false)
    private Long qcId;

    @Column(name = "user_id", length = 50, nullable = false)
    private String userId; // 처리자

    @Column(name = "remark", length = 255)
    private String remark;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "datetime default current_timestamp")
    private LocalDateTime createdAt; // 생성일

    @Column(name = "idempotency_key", length = 100, nullable = false)
    private String idempotencyKey; // 멱등키(UNIQUE)

    @Column(name = "work_order_id")
    private Long workOrderId; // 생산지시ID(선택)
}
