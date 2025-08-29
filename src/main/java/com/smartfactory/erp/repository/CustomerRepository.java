package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {

    //조회
    List<CustomerEntity> findByCustomerNmContaining (String customerNm);
    List<CustomerEntity> findByContractDate(LocalDate contractDate);
    List<CustomerEntity> findByCustomerNmContainingAndContractDate(String customerNm, LocalDate contractDate);

}

