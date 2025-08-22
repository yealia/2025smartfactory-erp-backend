package com.smartfactory.erp.controller;

import com.smartfactory.erp.entity.Customer;
import com.smartfactory.erp.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor //생성자 자동생성
public class CustomerController {

    private final CustomerService customerService;

    // 전체 조회
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // 단건 조회
    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable("id") String customer) {
        return customerService.getCustomerById(customer);
    }

    // 등록
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable("id") String customer) {
        customerService.deleteCustomer(customer);
    }

}
