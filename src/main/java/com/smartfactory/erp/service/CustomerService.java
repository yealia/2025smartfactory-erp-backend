package com.smartfactory.erp.service;

import com.smartfactory.erp.dto.CustomerDto;
import com.smartfactory.erp.entity.CustomerEntity;
import com.smartfactory.erp.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service //서비스
@RequiredArgsConstructor // final 필드 생성자 자동 생성 (의존성 주입)
public class CustomerService {

    //CustomerRepository통해 DB를 연결해야하니 접근하기 위해 선언
    private final CustomerRepository customerRepository;//불변

    // 전체 조회
    public List<CustomerEntity> getAllCustomers() {
        return customerRepository.findAll();
    }
    //조건절 조회
    public List<CustomerDto> getSearchCustomers(String customerNm, LocalDate contractDate){
        List<CustomerEntity> result;

        if (customerNm != null && contractDate != null) {
            result = customerRepository.findByCustomerNmContainingAndContractDate(customerNm, contractDate);
        } else if (customerNm != null) {
            result = customerRepository.findByCustomerNmContaining(customerNm);
        } else if (contractDate != null) {
            result = customerRepository.findByContractDate(contractDate);
        } else {
            result = customerRepository.findAll();
        }
        return result.stream()
                .map(CustomerDto::fromEntity) // 정상 작동
                .toList();
    }
    //상세 조회
    public CustomerEntity getCustomer(String customerId) {
        return customerRepository.findById(customerId)
                .orElse(null);
    }
    //등록/수정
    @Transactional
    public CustomerEntity saveCustomer(CustomerEntity customer){
        return customerRepository.save(customer);
    }
    //여러 건 저장
    @Transactional
    public List<CustomerEntity> saveAllCustomers(List<CustomerEntity> customers){
        return customerRepository.saveAll(customers);
    }
    //삭제
    @Transactional
    public void deleteCustomer(String customerId) {
        customerRepository.deleteById(customerId);
    }
}