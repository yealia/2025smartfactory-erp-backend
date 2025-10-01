package com.smartfactory.erp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartfactory.erp.entity.MaterialEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MaterialDto {
    private Integer materialId;
    private String materialNm;
    private String category;
    private String specification;
    private String unit;
    private BigDecimal unitPrice;
    private BigDecimal currentPrice;
    private Integer minStockQuantity;
    private Integer maxStockQuantity;
    private Integer leadTime;
    private LocalDate lastPurchaseDate;
    private Integer status;
    private String warehouse;
    private String location;
    private String remark;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    private Integer supplierId;
    private String supplierName;

    /**
     * DTO -> Entity 변환 (✅ Supplier 객체 설정은 Service 계층에서 처리하도록 파라미터 제거)
     */
    public MaterialEntity toEntity() {
        MaterialEntity entity = new MaterialEntity();
        entity.setMaterialId(this.materialId);
        entity.setMaterialNm(this.materialNm);
        entity.setCategory(this.category);
        entity.setSpecification(this.specification);
        entity.setUnit(this.unit);
        entity.setUnitPrice(this.unitPrice);
        entity.setCurrentPrice(this.currentPrice);
        entity.setMinStockQuantity(this.minStockQuantity);
        entity.setMaxStockQuantity(this.maxStockQuantity);
        entity.setLeadTime(this.leadTime);
        entity.setLastPurchaseDate(this.lastPurchaseDate);
        entity.setStatus(this.status);
        entity.setWarehouse(this.warehouse);
        entity.setLocation(this.location);
        entity.setRemark(this.remark);
        // supplier 객체 설정은 서비스 레이어에서 담당합니다.
        return entity;
    }

    /**
     * Entity -> DTO 변환 (✅ Supplier 객체가 null일 경우를 대비한 안전한 로직으로 수정)
     */
    public static MaterialDto fromEntity(MaterialEntity entity) {
        MaterialDto dto = new MaterialDto();
        dto.setMaterialId(entity.getMaterialId());
        dto.setMaterialNm(entity.getMaterialNm());
        dto.setCategory(entity.getCategory());
        dto.setSpecification(entity.getSpecification());
        dto.setUnit(entity.getUnit());
        dto.setUnitPrice(entity.getUnitPrice());
        dto.setCurrentPrice(entity.getCurrentPrice());
        dto.setMinStockQuantity(entity.getMinStockQuantity());
        dto.setMaxStockQuantity(entity.getMaxStockQuantity());
        dto.setLeadTime(entity.getLeadTime());
        // 연관된 Supplier 객체가 존재할 경우 그 ID를 가져옵니다.
        if (entity.getSupplier() != null) {
            dto.setSupplierId(entity.getSupplier().getSupplierId());
            dto.setSupplierName(entity.getSupplier().getSupplierName());
        }
        dto.setLastPurchaseDate(entity.getLastPurchaseDate());
        dto.setStatus(entity.getStatus());
        dto.setWarehouse(entity.getWarehouse());
        dto.setLocation(entity.getLocation());
        dto.setRemark(entity.getRemark());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}

