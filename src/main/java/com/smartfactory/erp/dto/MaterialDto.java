package com.smartfactory.erp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartfactory.erp.entity.CustomerEntity;
import com.smartfactory.erp.entity.MaterialEntity;
import com.smartfactory.erp.entity.SupplierEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**/
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
    private Integer currentStock;
    private Integer leadTime;
    private Integer supplierId;
    private LocalDate lastPurchaseDate;
    private Integer status;
    private String warehouse;
    private String location;
    private String remark;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;


    //dto -> entity로 변환
    public MaterialEntity toEntity(SupplierEntity supplierEntity){
        MaterialEntity entity = new MaterialEntity();
        entity.setMaterialId(this.materialId);
        entity.setMaterialNm(this.materialNm);
        entity.setCategory(this.category);
        entity.setSupplierId(this.supplierId);
        entity.setSpecification(this.specification);
        entity.setUnit(this.unit);
        entity.setUnitPrice(this.unitPrice);
        entity.setCurrentPrice(this.currentPrice);
        entity.setMinStockQuantity(this.minStockQuantity);
        entity.setMaxStockQuantity(this.maxStockQuantity);
        entity.setCurrentStock(this.currentStock);
        entity.setLeadTime(this.leadTime);
        entity.setSupplierId(this.supplierId); // FK 매핑
        entity.setLastPurchaseDate(this.lastPurchaseDate);
        entity.setStatus(this.status);
        entity.setRemark(this.remark);
        entity.setWarehouse(this.warehouse); //20250903 창고추가 
        entity.setLocation(this.location); //20250903 위치추가
        return entity;
    }
    //entity -> dto
    public static MaterialDto fromEntity(MaterialEntity entity){
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
        dto.setCurrentStock(entity.getCurrentStock());
        dto.setLeadTime(entity.getLeadTime());
        dto.setSupplierId(entity.getSupplierId());
        dto.setLastPurchaseDate(entity.getLastPurchaseDate());
        dto.setStatus(entity.getStatus());
        dto.setRemark(entity.getRemark());
        dto.setWarehouse(entity.getWarehouse()); //20250903 창고추가
        dto.setLocation(entity.getLocation()); //20250903 위치추가
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}