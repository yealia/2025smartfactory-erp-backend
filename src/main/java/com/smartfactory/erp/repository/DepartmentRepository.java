package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Integer>,
        JpaSpecificationExecutor<DepartmentEntity> {
    // 동적 쿼리 위해 JpaSpecificationExecutor 추가
}
