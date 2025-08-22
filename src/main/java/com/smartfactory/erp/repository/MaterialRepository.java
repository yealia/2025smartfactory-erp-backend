package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
    // 자재명으로 검색
    Material findByMaterialNm(String materialNm);
}
