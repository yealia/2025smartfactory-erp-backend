package com.smartfactory.erp.dto;

import com.smartfactory.erp.entity.CustomerEntity;
import lombok.Data;
import java.time.LocalDate;
import java.util.Date;

/**/
@Data
public class CustomerDto {
    private String customerId;
    private String customerNm;
    private LocalDate contractDate;
    private String contactPerson;
    private String contactPhone;
    private String contactEmail;
    private String contactAddress;

    // 조회/삭제
    //dto -> entity(db)로 변환
    public CustomerEntity toEntity(){
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCustomerId(this.customerId);
        customerEntity.setCustomerNm(this.customerNm);
        customerEntity.setContractDate(this.contractDate);
        customerEntity.setContactPerson(this.contactPerson);
        customerEntity.setContactPhone(this.contactPhone);
        customerEntity.setContactEmail(this.contactEmail);
        customerEntity.setContactAddress(this.contactAddress);
        return customerEntity;
    }

    // 수정/등록
    //entity(DB) -> dto
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