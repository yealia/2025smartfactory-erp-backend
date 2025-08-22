package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    Customer findByCustomerNm(String customerId);
}
