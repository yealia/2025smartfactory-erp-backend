package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Integer> {
    List<DepartmentEntity> findByDepartmentNmContaining(String departmentNm);
    List<DepartmentEntity> findByDepartmentIdAndDepartmentNmContaining(int departmentId, String departmentNm);
}