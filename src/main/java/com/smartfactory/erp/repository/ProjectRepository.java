package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.ProjectEntity;
import com.smartfactory.erp.entity.ProjectPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, String>
                                    , JpaSpecificationExecutor<ProjectEntity> {


}
