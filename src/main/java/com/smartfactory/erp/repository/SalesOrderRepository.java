package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.SalesOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrderEntity, String>, JpaSpecificationExecutor<SalesOrderEntity> {
}