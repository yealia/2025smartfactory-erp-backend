package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<PositionEntity, Integer> {
    // JpaRepository를 상속받는 것만으로 기본적인 CRUD 기능(findAll 포함)이 자동 생성됩니다.
}
