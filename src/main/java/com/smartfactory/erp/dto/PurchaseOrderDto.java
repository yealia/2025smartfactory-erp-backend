package com.smartfactory.erp.dto;

import com.smartfactory.erp.entity.PurchaseOrderEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PurchaseOrderDto {
    private String purchaseOrderId;
    private LocalDate orderDate;
    private LocalDate deliveryDate;
    private Integer supplierId;
    private String supplierName; // 클라이언트 편의를 위한 필드
    private Integer status;
    private BigDecimal totalAmount;
    private String createBy;
    private String approvedBy;
    private LocalDateTime approvedDate;
    private String remark;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PurchaseOrderEntity toEntity() {
        PurchaseOrderEntity entity = new PurchaseOrderEntity();
        entity.setPurchaseOrderId(this.getPurchaseOrderId());
        entity.setOrderDate(this.orderDate);
        entity.setDeliveryDate(this.deliveryDate);
        entity.setSupplierId(this.supplierId);
        entity.setStatus(this.status);
        entity.setTotalAmount(this.totalAmount);
        entity.setCreateBy(this.createBy);
        entity.setApprovedBy(this.approvedBy);
        entity.setApprovedDate(this.approvedDate);
        entity.setRemark(this.remark);
        entity.setCreatedAt(this.createdAt);
        entity.setUpdatedAt(this.updatedAt);
        return entity;
    }

    public static PurchaseOrderDto fromEntity(PurchaseOrderEntity entity) {
        PurchaseOrderDto dto = new PurchaseOrderDto();
        dto.setPurchaseOrderId(entity.getPurchaseOrderId());
        dto.setOrderDate(entity.getOrderDate());
        dto.setDeliveryDate(entity.getDeliveryDate());
        dto.setSupplierId(entity.getSupplierId());
        // 연관된 Supplier 객체가 로드되었다면 이름을 설정
        if (entity.getSupplier() != null && entity.getSupplier().getSupplierName() != null) {
            dto.setSupplierName(entity.getSupplier().getSupplierName());
        }
        dto.setStatus(entity.getStatus());
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setCreateBy(entity.getCreateBy());
        dto.setApprovedBy(entity.getApprovedBy());
        dto.setApprovedDate(entity.getApprovedDate());
        dto.setRemark(entity.getRemark());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}
