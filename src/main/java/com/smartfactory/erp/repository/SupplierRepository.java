package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
// JpaSpecificationExecutor<SupplierEntity>를 추가로 상속받습니다.
public interface SupplierRepository extends JpaRepository<SupplierEntity, Integer>, JpaSpecificationExecutor<SupplierEntity> {

}
