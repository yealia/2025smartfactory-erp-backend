package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, String>,
        JpaSpecificationExecutor<ProjectEntity> {
    // 기존의 동적 메서드는 이제 필요없음
}
