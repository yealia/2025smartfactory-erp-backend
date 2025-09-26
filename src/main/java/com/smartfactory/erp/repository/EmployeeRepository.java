package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, String>,
        JpaSpecificationExecutor<EmployeeEntity> {
}
