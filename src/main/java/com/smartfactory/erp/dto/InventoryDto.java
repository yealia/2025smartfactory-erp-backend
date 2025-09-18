package com.smartfactory.erp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartfactory.erp.entity.InventoryEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InventoryDto {
    private String inventoryId;
    private Integer materialId;
    private String warehouse;
    private String location;
    private Integer onHand;
    private Integer reservedQty;
    private Integer safetyStock;
    private Integer reorderPoint;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    private Integer version;
    private String remark;

    /** DTO -> Entity */
    public InventoryEntity toEntity() {
        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setInventoryId(this.inventoryId); // null이면 @PrePersist에서 자동 생성
        inventoryEntity.setMaterialId(this.materialId);
        inventoryEntity.setWarehouse(this.warehouse);
        inventoryEntity.setLocation(this.location);
        inventoryEntity.setOnHand(this.onHand != null ? this.onHand : 0);
        inventoryEntity.setReservedQty(this.reservedQty != null ? this.reservedQty : 0);
        inventoryEntity.setSafetyStock(this.safetyStock != null ? this.safetyStock : 0);
        inventoryEntity.setReorderPoint(this.reorderPoint != null ? this.reorderPoint : 0);
        // createdAt/updatedAt/version은 JPA가 관리
        return inventoryEntity;
    }
    /** Entity -> DTO */
    public static InventoryDto fromEntity(InventoryEntity entity) {
        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setInventoryId(entity.getInventoryId());
        inventoryDto.setMaterialId(entity.getMaterialId());
        inventoryDto.setWarehouse(entity.getWarehouse());
        inventoryDto.setLocation(entity.getLocation());
        inventoryDto.setOnHand(entity.getOnHand());
        inventoryDto.setReservedQty(entity.getReservedQty());
        inventoryDto.setSafetyStock(entity.getSafetyStock());
        inventoryDto.setReorderPoint(entity.getReorderPoint());
        inventoryDto.setCreatedAt(entity.getCreatedAt());
        inventoryDto.setUpdatedAt(entity.getUpdatedAt());
        inventoryDto.setVersion(entity.getVersion());
        return inventoryDto;
    }
}
