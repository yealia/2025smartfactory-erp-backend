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
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PurchaseDetailEntity toEntity(PurchaseOrderEntity purchaseOrderEntity) {
        PurchaseDetailEntity entity = new PurchaseDetailEntity();
        //신규저장이면 pk는 세팅하지않음
        if(this.orderDetailId != null){
            entity.setOrderDetailId(this.orderDetailId);
        }
        entity.setPurchaseOrder(purchaseOrderEntity);
        entity.setMaterialId(this.materialId);
        entity.setOrderQuantity(this.orderQuantity);
        entity.setUnitPrice(this.unitPrice);
        //amount
        if (this.unitPrice != null && this.orderQuantity != null) {
            entity.setAmount(this.unitPrice.multiply(
                    java.math.BigDecimal.valueOf(this.orderQuantity)
            ));
        } else {
            entity.setAmount(this.amount); // 이미 계산된 값이 넘어온다면
        }
        entity.setReceivedQuantity(this.receivedQuantity);
        entity.setStatus(this.status);
        //default
        //entity.setCreatedAt(this.createdAt);
        //entity.setUpdatedAt(this.updatedAt);
        return entity;
    }
    public static PurchaseDetailDto fromEntity(PurchaseDetailEntity entity) {
        PurchaseDetailDto dto = new PurchaseDetailDto();
        dto.setOrderDetailId(entity.getOrderDetailId());
        //dto.setPurchaseOrderId(entity.getPurchaseOrderId());
        dto.setPurchaseOrderId(entity.getPurchaseOrder().getPurchaseOrderId());
        dto.setMaterialId(entity.getMaterialId());
        dto.setOrderQuantity(entity.getOrderQuantity());
        dto.setUnitPrice(entity.getUnitPrice());
        dto.setAmount(entity.getAmount());
        dto.setReceivedQuantity(entity.getReceivedQuantity());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

}
