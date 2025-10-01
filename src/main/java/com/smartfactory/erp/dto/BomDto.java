package com.smartfactory.erp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartfactory.erp.entity.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BomDto {

    private Integer bomId;
    private Integer requiredQuantity;
    private String unit;
    private String remark;

    // --- 연관 관계 ID 필드 ---
    private String vesselId;
    private String processId;
    private Integer blockId;
    private Integer materialId;

    // --- 타임스탬프 필드 ---
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    private String materialName;

    /**
     * DTO -> Entity 변환
     */
    public BomEntity toEntity() {
        BomEntity entity = new BomEntity();
        entity.setBomId(this.bomId);
        entity.setRequiredQuantity(this.requiredQuantity);
        entity.setUnit(this.unit);
        entity.setRemark(this.remark);

        // ID를 사용하여 연관 엔티티의 '껍데기'를 만들어 설정합니다.
        // 서비스 레이어에서 이 ID를 사용해 실제 영속성 엔티티를 찾아 연결하게 됩니다.
        if (this.vesselId != null) {
            VesselEntity vessel = new VesselEntity();
            vessel.setVesselId(this.vesselId);
            entity.setVessel(vessel);
        }
        if (this.processId != null) {
            ProcessEntity process = new ProcessEntity();
            process.setProcessId(this.processId);
            entity.setProcess(process);
        }
        if (this.blockId != null) {
            BlockEntity block = new BlockEntity();
            block.setBlockId(this.blockId);
            entity.setBlock(block);
        }
        if (this.materialId != null) {
            MaterialEntity material = new MaterialEntity();
            material.setMaterialId(this.materialId);
            entity.setMaterial(material);
        }

        return entity;
    }

    /**
     * Entity -> DTO 변환
     */
    public static BomDto fromEntity(BomEntity entity) {
        BomDto dto = new BomDto();
        dto.setBomId(entity.getBomId());
        dto.setRequiredQuantity(entity.getRequiredQuantity());
        dto.setUnit(entity.getUnit());
        dto.setRemark(entity.getRemark());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        // 연관 엔티티가 null이 아닐 경우, ID를 추출하여 DTO에 설정합니다.
        if (entity.getVessel() != null) {
            dto.setVesselId(entity.getVessel().getVesselId());
        }
        if (entity.getProcess() != null) {
            dto.setProcessId(entity.getProcess().getProcessId());
        }
        if (entity.getBlock() != null) {
            dto.setBlockId(entity.getBlock().getBlockId());
        }
        if (entity.getMaterial() != null) {
            dto.setMaterialId(entity.getMaterial().getMaterialId());
            dto.setMaterialName(entity.getMaterial().getMaterialNm());
        }

        return dto;
    }
}