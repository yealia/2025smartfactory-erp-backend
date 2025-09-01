package com.smartfactory.erp.dto;

import com.smartfactory.erp.entity.VesselEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class VesselDto {
    private String vesselId;       // 선박ID
    private String vesselNm;       // 선박명
    private String vesselType;     // 선박유형 (컨테이너선/벌크선 등)
    private BigDecimal vesselLength; // 길이 (m)
    private BigDecimal vesselBeam;   // 폭 (m)
    private String cargoCapacity;    // 적재능력 (TEU, DWT 등)
    private String engineSpec;       // 엔진 스펙
    private BigDecimal totalWeight;  // 총중량

    public VesselEntity toEntity() {
        VesselEntity entity = new VesselEntity();
        entity.setVesselId(this.vesselId);
        entity.setVesselNm(this.vesselNm);
        entity.setVesselType(this.vesselType);
        entity.setVesselLength(this.vesselLength);
        entity.setVesselBeam(this.vesselBeam);
        entity.setCargoCapacity(this.cargoCapacity);
        entity.setEngineSpec(this.engineSpec);
        entity.setTotalWeight(this.totalWeight);
        return entity;
    }
    // Entity → DTO 변환
    public static VesselDto fromEntity(VesselEntity entity) {
        VesselDto dto = new VesselDto();
        dto.setVesselId(entity.getVesselId());
        dto.setVesselNm(entity.getVesselNm());
        dto.setVesselType(entity.getVesselType());
        dto.setVesselLength(entity.getVesselLength());
        dto.setVesselBeam(entity.getVesselBeam());
        dto.setCargoCapacity(entity.getCargoCapacity());
        dto.setEngineSpec(entity.getEngineSpec());
        dto.setTotalWeight(entity.getTotalWeight());
        return dto;
    }
}
