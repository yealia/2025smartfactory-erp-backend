package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByCustomerId(String customerId);
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from Customer c where c.customerId = :customerId")


    void deleteByCustomerId(String customerId);

}
