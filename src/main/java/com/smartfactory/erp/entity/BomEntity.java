package com.smartfactory.erp.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "boms")
public class BomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bom_id")
    private Integer bomId;

    // ============= 관계 매핑 =============

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vessel_id") // referencedColumnName은 보통 생략 가능합니다.
    private VesselEntity vessel; // 선박

    // 1. processId를 ProcessEntity와 매핑하도록 수정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_id")
    private ProcessEntity process; // 공정

    // 2. 누락된 block_id를 BlockEntity와 매핑하도록 추가
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "block_id")
    private BlockEntity block; // 블록

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id")
    private MaterialEntity material; // 자재

    // ==================================

    @Column(name = "required_quantity", nullable = false)
    private Integer requiredQuantity; // 소요수량

    @Column(name = "unit", nullable = false, length = 20)
    private String unit; // 단위 (EA, KG 등)

    @Column(name = "remark", length = 255)
    private String remark; // 비고

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // 생성일

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // 수정일
}
