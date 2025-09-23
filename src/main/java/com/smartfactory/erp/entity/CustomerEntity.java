package com.smartfactory.erp.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "customers")
public class CustomerEntity {

    @Id
    @Column(name = "customer_id", nullable = false, length = 20)
    private String customerId;

    @Column(name = "customer_nm", nullable = false, length = 50)
    private String customerNm;

    @Column(name = "business_registration", nullable = false, unique = true, length = 30)
    private String businessRegistration;

    @Column(name = "contract_date")
    private LocalDate contractDate;

    @Column(name = "country_code", nullable = false, length = 2)
    private String countryCode;

    @Column(name = "status", nullable = false, length = 20)
    private String status = "ACTIVE";

    @Column(name = "contact_person", length = 20)
    private String contactPerson;

    @Column(name = "contact_phone", length = 50)
    private String contactPhone;

    @Column(name = "contact_email", length = 50)
    private String contactEmail;

    @Column(name = "contact_address", length = 100)
    private String contactAddress;

    @Column(name = "remark", length = 255)
    private String remark;

    @Column(name = "created_at", updatable = false, insertable = false,
            columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false,
            columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;
}