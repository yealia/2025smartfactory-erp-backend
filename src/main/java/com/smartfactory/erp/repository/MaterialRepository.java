package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.MaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaterialRepository extends JpaRepository<MaterialEntity, Integer>, JpaSpecificationExecutor<MaterialEntity> {
    Optional<MaterialEntity> findByMaterialNm(String materialNm);
}

