package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.MaterialEntity;
import com.smartfactory.erp.entity.MaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<MaterialEntity, Integer> {

    //조회
    List<MaterialEntity> findByMaterialNmContaining (String materialNm);
    List<MaterialEntity> findByContractDate(LocalDate contractDate);
    List<MaterialEntity> findByMaterialNmContainingAndContractDate(String materialNm, LocalDate contractDate);
}
