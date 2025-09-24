package com.smartfactory.erp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartfactory.erp.entity.MovementEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MovementDto {
    private Integer movementId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
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
    private Integer qcId;
    private String userId;
    private String remark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    private String idempotencyKey;
    private Integer workOrderId;

    /** DTO -> Entity (빌더 사용 안 함) */
    public MovementEntity toEntity() {
        MovementEntity entity = new MovementEntity();
        entity.setMovementId(this.movementId);
        entity.setOccurredAt(this.occurredAt);
        entity.setMaterialId(this.materialId);
        entity.setQty(this.qty);
        entity.setWarehouseFrom(this.warehouseFrom);
        entity.setWarehouseTo(this.warehouseTo);
        entity.setLocationFrom(this.locationFrom);
        entity.setLocationTo(this.locationTo);
        entity.setMovementType(this.movementType);
        entity.setSourceType(this.sourceType);
        entity.setPurchaseOrderId(this.purchaseOrderId);
        entity.setOrderDetailId(this.orderDetailId);
        entity.setQcId(this.qcId);
        entity.setUserId(this.userId);
        entity.setRemark(this.remark);
        entity.setIdempotencyKey(this.idempotencyKey);
        entity.setWorkOrderId(this.workOrderId);
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
