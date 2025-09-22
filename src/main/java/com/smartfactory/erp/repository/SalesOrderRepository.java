package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.SalesOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrderEntity, String> {
    List<SalesOrderEntity> findByCustomerIdContaining(String customerId);
    List<SalesOrderEntity> findByVesselIdContaining(String vesselId);
    List<SalesOrderEntity> findByCustomerIdContainingAndVesselIdContaining(String customerId, String vesselId);
}