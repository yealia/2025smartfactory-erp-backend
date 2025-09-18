package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.SupplierEntity;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Integer> {
    List<SupplierEntity> findBySupplierNameContaining(String supplierName);
    List<SupplierEntity> findByContractDate(LocalDate contractDate);
    List<SupplierEntity> findBySupplierNameContainingAndContractDate(String supplierName, LocalDate contractDate);
}
