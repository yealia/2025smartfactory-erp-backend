package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.BomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BomRepository extends JpaRepository<BomEntity, Integer>,
        JpaSpecificationExecutor<BomEntity> {
}
