package com.smartfactory.erp.dto;

import com.smartfactory.erp.entity.BomEntity;
import com.smartfactory.erp.entity.MaterialEntity;
import com.smartfactory.erp.entity.VesselEntity;
import lombok.Data;

@Data
public class BomDto {
    private Integer bomId;           // BOM ID
    private String vesselId;         // 선박ID (FK)
    private Integer materialId;      // 자재ID (FK)
    private Integer requiredQuantity;// 소요수량
    private String unit;             // 단위
    private String processId;        // 공정ID
    private String remark;           // 비고
    // dto -> entity 변환
    public BomEntity toEntity(VesselEntity vesselEntity, MaterialEntity materialEntity) {
        BomEntity entity = new BomEntity();
        entity.setBomId(this.bomId);
        entity.setVessel(vesselEntity);
        entity.setMaterial(materialEntity);
        entity.setRequiredQuantity(this.requiredQuantity);
        entity.setUnit(this.unit);
        entity.setProcessId(this.processId);
        entity.setRemark(this.remark);
        return entity;
    }
    // entity -> dto 변환
    public static BomDto fromEntity(BomEntity entity) {
        BomDto dto = new BomDto();
        dto.setBomId(entity.getBomId());
        dto.setVesselId(entity.getVessel() != null ? entity.getVessel().getVesselId() : null);
        dto.setMaterialId(entity.getMaterial() != null ? entity.getMaterial().getMaterialId() : null);
        dto.setRequiredQuantity(entity.getRequiredQuantity());
        dto.setUnit(entity.getUnit());
        dto.setProcessId(entity.getProcessId());
        dto.setRemark(entity.getRemark());
        return dto;
    }
}
