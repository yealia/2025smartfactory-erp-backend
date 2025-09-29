package com.smartfactory.erp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartfactory.erp.entity.VesselEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class VesselDto {
    private String vesselId;
    private String vesselNm;
    private String vesselType;
    private BigDecimal vesselLength;
    private BigDecimal vesselBeam;
    private String cargoCapacity;
    private String engineSpec;
    private BigDecimal totalWeight;
    private Integer status;
    private BigDecimal vesselDepth;
    private LocalDate actualDeliveryDate;
    private String remark;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;


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
        entity.setStatus(this.status);
        entity.setVesselDepth(this.vesselDepth);
        entity.setActualDeliveryDate(this.actualDeliveryDate);
        entity.setRemark(this.remark);
        return entity;
    }

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
        dto.setStatus(entity.getStatus());
        dto.setVesselDepth(entity.getVesselDepth());
        dto.setActualDeliveryDate(entity.getActualDeliveryDate());


        dto.setRemark(entity.getRemark());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}