package com.smartfactory.erp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "suppliers")
public class SupplierEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT 매핑
    @Column(name = "supplier_id", nullable = false)
    private Integer supplierId;  // 공급업체 ID (PK)

    @Column(name = "supplier_nm", length = 50, nullable = false)
    private String supplierNm;   // 업체명

    @Column(name = "contract_date")
    private LocalDate contractDate; //등록 날짜

    @Column(name = "contact_person", length = 20)
    private String contactPerson; // 담당자명

    @Column(name = "contact_phone", length = 20)
    private String contactPhone;  // 연락처

    @Column(name = "contact_address", length = 100)
    private String contactAddress; // 주소

    @Column(name = "contact_email", length = 50)
    private String contactEmail;  // 이메일
}
