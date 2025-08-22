package com.smartfactory.erp.service;

import com.smartfactory.erp.entity.Customer;
import com.smartfactory.erp.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    // 전체 조회
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // 단건 조회 (PK 기준)
    public Customer getCustomerById(String customer) {
        return customerRepository.findById(customer)
                .orElseThrow(() -> new RuntimeException("고객을 찾을 수 없습니다. ID=" + customer));
    }

    // 등록
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // 삭제 (PK 기준)
    public void deleteCustomer(String customer) {
        customerRepository.deleteById(customer);
    }
}