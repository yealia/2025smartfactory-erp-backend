package com.smartfactory.erp.dto;

import com.smartfactory.erp.entity.SupplierEntity;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SupplierDto {

    private Integer supplierId;      // 공급업체 ID
    private String supplierNm;       // 업체명
    private LocalDate contractDate;  // 등록 날짜
    private String contactPerson;    // 담당자명
    private String contactPhone;     // 연락처
    private String contactAddress;   // 주소
    private String contactEmail;     // 이메일

    // DTO -> Entity 변환
    public SupplierEntity toEntity() {
        SupplierEntity entity = new SupplierEntity();
        entity.setSupplierId(this.supplierId);
        entity.setSupplierNm(this.supplierNm);
        entity.setContractDate(this.contractDate);
        entity.setContactPerson(this.contactPerson);
        entity.setContactPhone(this.contactPhone);
        entity.setContactAddress(this.contactAddress);
        entity.setContactEmail(this.contactEmail);
        return entity;
    }

    // Entity -> DTO 변환
    public static SupplierDto fromEntity(SupplierEntity entity) {
        SupplierDto dto = new SupplierDto();
        dto.setSupplierId(entity.getSupplierId());
        dto.setSupplierNm(entity.getSupplierNm());
        dto.setContractDate(entity.getContractDate());
        dto.setContactPerson(entity.getContactPerson());
        dto.setContactPhone(entity.getContactPhone());
        dto.setContactAddress(entity.getContactAddress());
        dto.setContactEmail(entity.getContactEmail());
        return dto;
    }


}
