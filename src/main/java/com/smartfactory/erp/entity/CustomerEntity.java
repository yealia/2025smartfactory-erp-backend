package com.smartfactory.erp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data //getter/setter/toString/Equals/HashCode
@Entity //DB에서 어떤 테이블과 연결할지 지정
@Table(name = "customers") //customers 테이블

public class CustomerEntity {
    @Id
    @Column(name = "customer_id", length = 20, nullable = false)
    private String customerId;

    @Column(name = "customer_nm", length = 50, nullable = false)
    private String customerNm;

    @Column(name = "contract_date")
    private LocalDate contractDate;
    //Date : 불변 객체가 아니라서 값을 바꿀 수 있음 -> 버그 위험, 날짜와 시간 정보가 섞여있음
    // Date 타입 쓸려면 @Temporal(TemporalType.DATE)써야함
    // LocalDate : 날짜(연,원,일)다룸, 불변 객체라 안전함

    @Column(name = "contact_person", length = 20)
    private String contactPerson;

    @Column(name = "contact_phone", length = 50)
    private String contactPhone;

    @Column(name = "contact_email", length = 50)
    private String contactEmail;

    @Column(name = "contact_address", length = 100)
    private String contactAddress;
}