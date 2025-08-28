package com.smartfactory.erp.controller;

import com.smartfactory.erp.dto.CustomerDto;
import com.smartfactory.erp.entity.CustomerEntity;
import com.smartfactory.erp.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController //컨트롤러
@RequestMapping("/api/customers") //기본 URL지정
@RequiredArgsConstructor //final붙은 필드 @NonNull붙은 필드만 생성자 자동생성
public class CustomerController {

    private final CustomerService customerService;

    // 전체 조회
//    @GetMapping
//    public List<Customer> getAllCustomers() {
//        return customerService.getAllCustomers();
//    }

    //조건 조회
    @GetMapping
    public List<CustomerDto> getCustomers( String customerNm, LocalDate contractDate){
        return customerService.getSearchCustomers(customerNm, contractDate);
    }

    // Querydsl 조건 검색 (고객명 + 계약일)
//    @GetMapping()
//    public List<CustomerDto> getCustomers(
//            @RequestParam(required = false) String customerNm,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate contractDate
//    ) {
//        return customerService.getSearchCustomers(customerNm, contractDate);
//    }

    // 단건 조회
    @GetMapping("/{customerId}")
    public CustomerEntity getCustomer(@PathVariable("customerId") String customer) {
        return customerService.getCustomer(customer);
    }

    //저장
    @PostMapping("")
    public CustomerEntity saveCustomer(@RequestBody CustomerEntity customer){
        return customerService.saveCustomer(customer);
    }

    //모두저장
    @PostMapping("/saveAll")
    public List<CustomerEntity> saveAll(@RequestBody List<CustomerEntity> customers) {
        return customerService.saveAllCustomers(customers);
        // JPA가 id==null → insert, id!=null → update 처리
    }

    // 삭제
    @DeleteMapping("/{customerId}")
    public void deleteCustomer(@PathVariable String customerId) {
        customerService.deleteCustomer(customerId);
    }
}
