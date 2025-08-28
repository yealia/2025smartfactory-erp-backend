package com.smartfactory.erp.repository;

import com.smartfactory.erp.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
/* JPA가 CRUD해줌 커스텀 쿼리가 필요할 경우만 코드 작성
findAll() → 전체 조회
findById(ID id) → PK 단건 조회
save(entity) → 등록/수정 (PK 있으면 update, 없으면 insert)
saveAll(entities) → 여러 건 등록/수정
delete(entity) → 엔티티 삭제
deleteById(ID id) → PK로 삭제
existsById(ID id) → 존재 여부 확인
count() → 총 레코드 수 */
@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {

    // customerNm LIKE %keyword% AND contractDate = ?
    List<CustomerEntity> findByCustomerNmContainingAndContractDate(String customerNm, LocalDate contractDate);

    // customerNm LIKE %keyword%
    List<CustomerEntity> findByCustomerNmContaining(String customerNm);

    // contractDate = ?
    List<CustomerEntity> findByContractDate(LocalDate contractDate);
}

