package com.smartfactory.erp.dto;

import com.smartfactory.erp.entity.PurchaseDetailEntity;
import com.smartfactory.erp.entity.PurchaseOrderEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PurchaseDetailDto {
    private Integer orderDetailId;
    private String purchaseOrderId;
    private Integer materialId;

    private Integer orderQuantity;
    private BigDecimal unitPrice;
    private BigDecimal amount;
    private Integer receivedQuantity;
    private Integer inspectedQuantity;
    private Integer inspectionStatus;
    private Integer status;
    private String remark;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PurchaseDetailEntity toEntity() {
        PurchaseDetailEntity entity = new PurchaseDetailEntity();
        // ID 및 연관관계 ID를 제외한 필드들을 설정
        entity.setOrderQuantity(this.orderQuantity);
        entity.setUnitPrice(this.unitPrice);
        entity.setAmount(this.amount);
        entity.setReceivedQuantity(this.receivedQuantity);
        entity.setInspectedQuantity(this.inspectedQuantity);
        entity.setInspectionStatus(this.inspectionStatus);
        entity.setStatus(this.status);
        entity.setRemark(this.remark);
        return entity;
    }

    public static PurchaseDetailDto fromEntity(PurchaseDetailEntity entity) {
        PurchaseDetailDto dto = new PurchaseDetailDto();
        dto.setOrderDetailId(entity.getOrderDetailId());
        dto.setPurchaseOrderId(entity.getPurchaseOrderId());
        dto.setMaterialId(entity.getMaterialId());

        dto.setOrderQuantity(entity.getOrderQuantity());
        dto.setUnitPrice(entity.getUnitPrice());
        dto.setAmount(entity.getAmount());
        dto.setReceivedQuantity(entity.getReceivedQuantity());
        dto.setInspectedQuantity(entity.getInspectedQuantity());
        dto.setInspectionStatus(entity.getInspectionStatus());
        dto.setStatus(entity.getStatus());
        dto.setRemark(entity.getRemark());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}
