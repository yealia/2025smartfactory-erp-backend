package com.smartfactory.erp.dto;

import com.smartfactory.erp.entity.SupplierEntity;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SupplierDto {

    private Integer supplierId;
    private String supplierName;
    private LocalDate contractDate;
    private String contactName;
    private String contactPhone;
    private String contactAddress;
    private String contactEmail;

    // DTO -> Entity 변환
    public SupplierEntity toEntity() {
        SupplierEntity entity = new SupplierEntity();
        entity.setSupplierId(this.supplierId);
        entity.setSupplierName(this.supplierName);
        entity.setContractDate(this.contractDate);
        entity.setContactName(this.contactName);
        entity.setContactPhone(this.contactPhone);
        entity.setContactAddress(this.contactAddress);
        entity.setContactEmail(this.contactEmail);
        return entity;
    }

    // Entity -> DTO 변환
    public static SupplierDto fromEntity(SupplierEntity entity) {
        SupplierDto dto = new SupplierDto();
        dto.setSupplierId(entity.getSupplierId());
        dto.setSupplierName(entity.getSupplierName());
        dto.setContractDate(entity.getContractDate());
        dto.setContactName(entity.getContactName());
        dto.setContactPhone(entity.getContactPhone());
        dto.setContactAddress(entity.getContactAddress());
        dto.setContactEmail(entity.getContactEmail());
        return dto;
    }
}