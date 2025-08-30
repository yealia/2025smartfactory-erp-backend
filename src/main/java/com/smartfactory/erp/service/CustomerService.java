package com.smartfactory.erp.service;

import com.smartfactory.erp.dto.CustomerDto;
import com.smartfactory.erp.entity.CustomerEntity;
import com.smartfactory.erp.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
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
@Slf4j
@Service //서비스
@RequiredArgsConstructor // final 필드 생성자 자동 생성 (의존성 주입)
public class CustomerService {

    //CustomerRepository통해 DB를 연결해야하니 접근하기 위해 선언
    private final CustomerRepository customerRepository;//불변

    // 전체 조회
//    public List<CustomerEntity> getAllCustomers() {
//        return customerRepository.findAll();
//    }

    //조건둘다 없을 경우
    public List<CustomerDto> getAllSearch(){
        return customerRepository.findAll()
                .stream()
                .map(CustomerDto::fromEntity)
                .toList();
    }
    //고객명만 있을 때
    public List<CustomerDto> getAllSearchCustomer(String customerNm){
        return customerRepository.findByCustomerNmContaining(customerNm)
                .stream()
                .map(CustomerDto::fromEntity)
                .toList();
    }
    //날짜만 있을때
    public List<CustomerDto> getAllSearchContractDate(LocalDate contractDate){
        System.out.println(contractDate);
        log.info("parameter = {}",contractDate);
        return customerRepository.findByContractDate(contractDate)
                .stream()
                .map(CustomerDto::fromEntity)
                .toList();
    }
    //조건 둘 다 있을 때
    public List<CustomerDto> getAllSearchCustomerContractDate(String customerNm, LocalDate contractDate){
        log.info("Controller 들어옴: customerNm={}, contractDate={}", customerNm, contractDate);
        return customerRepository.findByCustomerNmContainingAndContractDate(customerNm, contractDate)
                .stream()
                .map(CustomerDto::fromEntity)
                .toList();
    }

    //상세 조회
    public CustomerDto getCustomer(String customerId) {
        return customerRepository.findById(customerId)
                .map(CustomerDto::fromEntity)
                .orElse(null);
    }
    // =========================
    // 등록 / 수정
    // =========================
    @Transactional
    public CustomerDto saveCustomer(CustomerDto customer){
        //1. 디티오 받음
        //2. 엔티티로 바꿔라
        //3. 레파에 보내라
        //4. 디티오로 바꿔라
        CustomerEntity customerEntity = customerRepository.save(customer.toEntity());
        return CustomerDto.fromEntity(customerEntity);
    }
    //여러 건 저장
    @Transactional
    public List<CustomerDto> saveAllCustomers(List<CustomerDto> customers){
        //1. 디티오 받음
        //2. 엔티티로 바꿔라
        //3. 레파에 보내라
        //4. 디티오로 바꿔라
        List<CustomerEntity> customerEntity = customers.stream()
                                                        .map(CustomerDto::toEntity)
                                                        .toList();
        //레파지토리에 넣어야
        List<CustomerEntity> saveAll = customerRepository.saveAll(customerEntity);
        return saveAll.stream().map(CustomerDto::fromEntity).toList();
    }
    @Transactional
    public void deleteCustomer(String customerId) {
        customerRepository.deleteById(customerId);
    }
}