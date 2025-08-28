package com.smartfactory.erp.dto;

import com.smartfactory.erp.entity.CustomerEntity;
import lombok.Data;
import java.time.LocalDate;

@Data public class CustomerDto {
    private String customerId;
    private String customerNm;
    private LocalDate contractDate;
    private String contactPerson;
    private String contactPhone;
    private String contactEmail;
    private String contactAddress;

    public static CustomerDto fromEntity(CustomerEntity entity){
        CustomerDto dto = new CustomerDto();
            dto.setCustomerId(entity.getCustomerId());
            dto.setCustomerNm(entity.getCustomerNm());
            dto.setContractDate(entity.getContractDate());
            dto.setContactPerson(entity.getContactPerson());
            dto.setContactPhone(entity.getContactPhone());
            dto.setContactEmail(entity.getContactEmail());
            dto.setContactAddress(entity.getContactAddress());
            return dto;
    }
}