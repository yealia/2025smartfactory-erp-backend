package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.ProjectPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectPlanRepository extends JpaRepository<ProjectPlanEntity, String>, JpaSpecificationExecutor<ProjectPlanEntity> {
    Optional<ProjectPlanEntity> findTopByPlanIdStartingWithOrderByPlanIdDesc(String prefix);
}
