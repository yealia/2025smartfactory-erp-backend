package com.smartfactory.erp.dto;

import com.smartfactory.erp.entity.CustomerEntity;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CustomerDto {
    private String customerId;
    private String customerNm;
    private String businessRegistration;
    private LocalDate contractDate;
    private String countryCode;
    private String status;
    private String contactPerson;
    private String contactPhone;
    private String contactEmail;
    private String contactAddress;
    private String remark;

    // DTO -> Entity 변환
    public CustomerEntity toEntity(){
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCustomerId(this.customerId);
        customerEntity.setCustomerNm(this.customerNm);
        customerEntity.setBusinessRegistration(this.businessRegistration);
        customerEntity.setContractDate(this.contractDate);
        customerEntity.setCountryCode(this.countryCode);
        customerEntity.setStatus(this.status);
        customerEntity.setContactPerson(this.contactPerson);
        customerEntity.setContactPhone(this.contactPhone);
        customerEntity.setContactEmail(this.contactEmail);
        customerEntity.setContactAddress(this.contactAddress);
        customerEntity.setRemark(this.remark);
        return customerEntity;
    }

    // Entity -> DTO 변환
    public static CustomerDto fromEntity(CustomerEntity entity){
        CustomerDto dto = new CustomerDto();
        dto.setCustomerId(entity.getCustomerId());
        dto.setCustomerNm(entity.getCustomerNm());
        dto.setBusinessRegistration(entity.getBusinessRegistration());
        dto.setContractDate(entity.getContractDate());
        dto.setCountryCode(entity.getCountryCode());
        dto.setStatus(entity.getStatus());
        dto.setContactPerson(entity.getContactPerson());
        dto.setContactPhone(entity.getContactPhone());
        dto.setContactEmail(entity.getContactEmail());
        dto.setContactAddress(entity.getContactAddress());
        dto.setRemark(entity.getRemark());
        return dto;
    }
}