package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, String> {
    List<EmployeeEntity> findByEmployeeNmContaining(String employeeNm);
    List<EmployeeEntity> findByDepartment_DepartmentNmContaining(String departmentNm);
    List<EmployeeEntity> findByEmployeeNmContainingAndDepartment_DepartmentNmContaining(String employeeNm, String departmentNm);

}