package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.VesselEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VesselRepository extends JpaRepository<VesselEntity, String>,
        JpaSpecificationExecutor<VesselEntity> {
}
