package com.smartfactory.erp.controller;

import com.smartfactory.erp.dto.CustomerDto;
import com.smartfactory.erp.entity.CustomerEntity;
import com.smartfactory.erp.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController //컨트롤러
@RequestMapping("/api/customers") //기본 URL지정
@RequiredArgsConstructor //final붙은 필드 @NonNull붙은 필드만 생성자 자동생성
public class CustomerController {

    private final CustomerService customerService;

    //조건 조회
    @GetMapping
    public List<CustomerDto> getCustomers( String customerNm, LocalDate contractDate){
        //고객명 , 등록날짜 둘다 있는 경우
        if (customerNm != null && contractDate != null) {
            return customerService.getAllSearchCustomerContractDate(customerNm, contractDate);
        } else if (customerNm != null) {
            return customerService.getAllSearchCustomer(customerNm);
        } else if (contractDate != null) {
            return customerService.getAllSearchContractDate(contractDate);
        } else {
            return customerService.getAllSearch();
        }
    }

    // 단건 조회
    @GetMapping("/{customerId}")
    public CustomerDto getCustomer(@PathVariable("customerId") String customerId) {
        return customerService.getCustomer(customerId);}

    //저장
    @PostMapping("")
    public CustomerDto createCustomer(@RequestBody CustomerDto customer){
        return customerService.saveCustomer(customer);
    }

    //모두저장
    @PostMapping("/saveAll")
    public List<CustomerDto> saveAll(@RequestBody List<CustomerDto> customers) {
        return customerService.saveAllCustomers(customers);
        // JPA가 id==null → insert, id!=null → update 처리
    }

    @PutMapping("/{customerId}")
    public CustomerDto updateCustomer(@PathVariable String customerId, @RequestBody CustomerDto customerDto) {
        customerDto.setCustomerId(customerId);
        return customerService.saveCustomer(customerDto);
    }

    // 삭제
    @DeleteMapping("/{customerId}")
    public void deleteCustomer(@PathVariable String customerId) {
        customerService.deleteCustomer(customerId);
    }
}