package com.smartfactory.erp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "customers")
@Data
public class Customer {
    // 내부 식별자 (AUTO_INCREMENT)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 업무용 고객 코드 (예: CUST001)
    @Column(name = "customer_id", nullable = false, unique = true, length = 20)
    private String customerId;

    // 고객명
    @Column(name = "customer_nm", nullable = false, length = 50)
    private String customerNm;

    // 계약일자
    @Column(name = "contract_date")
    private java.sql.Date contractDate;

    // 담당자
    @Column(name = "contact_person", length = 20)
    private String contactPerson;

    // 연락처
    @Column(name = "contact_phone", length = 50)
    private String contactPhone;

    // 이메일
    @Column(name = "contact_email", length = 50)
    private String contactEmail;

    // 주소
    @Column(name = "contact_address", length = 100)
    private String contactAddress;
}
