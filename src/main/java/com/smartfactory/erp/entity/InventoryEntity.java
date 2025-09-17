package com.smartfactory.erp.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data //getter/setter/toString/Equals/HashCode
@Entity //DB에서 어떤 테이블과 연결할지 지정
@Table(name = "inventory") //inventory 테이블
public class InventoryEntity {
    //재고ID
    @Id
    @Column(name = "inventory_id", length = 20, nullable = false)
    private String inventoryId;

    //자재ID
    @Column(name = "material_id", nullable = false)
    private Integer materialId;

    //창고
    @Column(name = "warehouse", length = 20, nullable = false)
    private String warehouse;

    //위치
    @Column(name = "location", length = 50, nullable = false)
    private String location;

    //현재고
    @Column(name = "on_hand", nullable = false)
    private Integer onHand = 0;

    //예약수량
    @Column(name = "reserved_qty", nullable = false)
    private Integer reservedQty = 0;

    //안전재고
    @Column(name = "safety_stock", nullable = false)
    private Integer safetyStock = 0;

    //재주문점
    @Column(name = "reorder_point", nullable = false)
    private Integer reorderPoint = 0;

    @Column(name = "remark", length = 255)
    private String remark;

    //생성일
    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false, columnDefinition = "datetime default current_timestamp")
    private LocalDateTime createdAt;

    //수정일
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "datetime on update current_timestamp")
    private LocalDateTime updatedAt;

    //버전
    @Version
    @Column(name = "version", nullable = false)
    private Integer version = 0;



}
