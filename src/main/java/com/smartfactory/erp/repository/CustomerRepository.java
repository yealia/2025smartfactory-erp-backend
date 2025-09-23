// CustomerRepository.java

package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
// ✅ JpaSpecificationExecutor<CustomerEntity>를 추가로 상속받습니다.
public interface CustomerRepository extends JpaRepository<CustomerEntity, String>, JpaSpecificationExecutor<CustomerEntity> {
    // 기존의 정적 메소드들은 동적 쿼리로 대체되므로 삭제합니다.
}