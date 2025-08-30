package com.smartfactory.erp.dto;

import com.smartfactory.erp.entity.CustomerEntity;
import com.smartfactory.erp.entity.MaterialEntity;
import com.smartfactory.erp.entity.SupplierEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**/
@Data
public class MaterialDto {
    private Integer materialId;   // 자재ID
    private String materialNm;    // 자재명
    private String category;      // 자재분류
    private LocalDate contractDate;    //등록일
    private String specification; // 규격
    private String unit;          // 단위
    private BigDecimal unitPrice; // 기준단가
    private BigDecimal currentPrice; // 현재단가
    private Integer minStockQuantity; // 최소재고
    private Integer maxStockQuantity; // 최대재고
    private Integer currentStock;     // 현재고
    private Integer leadTime;         // 리드타임(일)
    private Integer supplierId;       // 공급업체 ID
    private LocalDate lastPurchaseDate; // 최근구매일
    private Integer status;           // 상태 (0:사용중, 1:단종, 2:대체필요)
    private String remark;            // 비고

    //dto -> entity로 변환
    public MaterialEntity toEntity(SupplierEntity supplierEntity){
        MaterialEntity entity = new MaterialEntity();
        entity.setMaterialId(this.materialId);
        entity.setMaterialNm(this.materialNm);
        entity.setCategory(this.category);
        entity.setContractDate(this.contractDate);
        entity.setSpecification(this.specification);
        entity.setUnit(this.unit);
        entity.setUnitPrice(this.unitPrice);
        entity.setCurrentPrice(this.currentPrice);
        entity.setMinStockQuantity(this.minStockQuantity);
        entity.setMaxStockQuantity(this.maxStockQuantity);
        entity.setCurrentStock(this.currentStock);
        entity.setLeadTime(this.leadTime);
        entity.setSupplier(supplierEntity); // FK 매핑
        entity.setLastPurchaseDate(this.lastPurchaseDate);
        entity.setStatus(this.status);
        entity.setRemark(this.remark);
        return entity;
    }
    //entity -> dto
    public static MaterialDto fromEntity(MaterialEntity entity){
        MaterialDto dto = new MaterialDto();
        dto.setMaterialId(entity.getMaterialId());
        dto.setMaterialNm(entity.getMaterialNm());
        dto.setCategory(entity.getCategory());
        dto.setContractDate(entity.getContractDate());
        dto.setSpecification(entity.getSpecification());
        dto.setUnit(entity.getUnit());
        dto.setUnitPrice(entity.getUnitPrice());
        dto.setCurrentPrice(entity.getCurrentPrice());
        dto.setMinStockQuantity(entity.getMinStockQuantity());
        dto.setMaxStockQuantity(entity.getMaxStockQuantity());
        dto.setCurrentStock(entity.getCurrentStock());
        dto.setLeadTime(entity.getLeadTime());
        if (entity.getSupplier() != null) {
            dto.setSupplierId(entity.getSupplier().getSupplierId());
        }
        dto.setLastPurchaseDate(entity.getLastPurchaseDate());
        dto.setStatus(entity.getStatus());
        dto.setRemark(entity.getRemark());
        return dto;
    }
}