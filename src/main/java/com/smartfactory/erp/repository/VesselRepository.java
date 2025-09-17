package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.VesselEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VesselRepository extends JpaRepository <VesselEntity, String> {
    List<VesselEntity> findByVesselNmContaining(String vesselNm);
    List<VesselEntity> findByVesselIdContaining(String vesselId);
    List<VesselEntity> findByVesselIdContainingAndVesselNmContaining(String vesselId, String vesselNm);
}

