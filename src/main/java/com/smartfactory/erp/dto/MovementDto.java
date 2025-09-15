package com.smartfactory.erp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartfactory.erp.entity.MovementEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MovementDto {
    private Integer movementId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime occurredAt;
    private Integer materialId;
    private Integer qty;
    private String warehouseFrom;
    private String warehouseTo;
    private String locationFrom;
    private String locationTo;
    private String movementType;
    private String sourceType;
    private String purchaseOrderId;
    private Integer orderDetailId;
    private Long qcId;
    private String userId;
    private String remark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    private String idempotencyKey;
    private Long workOrderId;

    /** DTO -> Entity (빌더 사용 안 함) */
    public MovementEntity toEntity() {
        MovementEntity entity = new MovementEntity();
        entity.setMovementId(this.getMovementId());            // 보통 null (AUTO_INCREMENT)
        entity.setOccurredAt(this.getOccurredAt());
        entity.setMaterialId(this.getMaterialId());
        entity.setQty(this.getQty());
        entity.setWarehouseFrom(this.getWarehouseFrom());
        entity.setWarehouseTo(this.getWarehouseTo());
        entity.setLocationFrom(this.getLocationFrom());
        entity.setLocationTo(this.getLocationTo());
        entity.setMovementType(this.getMovementType());
        entity.setSourceType(this.getSourceType());
        entity.setPurchaseOrderId(this.getPurchaseOrderId());
        entity.setOrderDetailId(this.getOrderDetailId());
        entity.setQcId(this.getQcId());
        entity.setUserId(this.getUserId());
        entity.setRemark(this.getRemark());
        // createdAt은 @CreationTimestamp로 DB에서 자동 세팅
        entity.setIdempotencyKey(this.getIdempotencyKey());
        entity.setWorkOrderId(this.getWorkOrderId());
        return entity;
    }

    public static MovementDto fromEntity(MovementEntity entity) {
        MovementDto dto = new MovementDto();
        dto.setMovementId(entity.getMovementId());
        dto.setOccurredAt(entity.getOccurredAt());
        dto.setMaterialId(entity.getMaterialId());
        dto.setQty(entity.getQty());
        dto.setWarehouseFrom(entity.getWarehouseFrom());
        dto.setWarehouseTo(entity.getWarehouseTo());
        dto.setLocationFrom(entity.getLocationFrom());
        dto.setLocationTo(entity.getLocationTo());
        dto.setMovementType(entity.getMovementType());
        dto.setSourceType(entity.getSourceType());
        dto.setPurchaseOrderId(entity.getPurchaseOrderId());
        dto.setOrderDetailId(entity.getOrderDetailId());
        dto.setQcId(entity.getQcId());
        dto.setUserId(entity.getUserId());
        dto.setRemark(entity.getRemark());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setIdempotencyKey(entity.getIdempotencyKey());
        dto.setWorkOrderId(entity.getWorkOrderId());
        return dto;
    }
}
