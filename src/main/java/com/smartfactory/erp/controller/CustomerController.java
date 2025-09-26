// CustomerController.java

package com.smartfactory.erp.controller;

import com.smartfactory.erp.dto.CustomerDto;
import com.smartfactory.erp.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // =========================
    // 동적 조건 검색
    // =========================
    @GetMapping
    public ResponseEntity<List<CustomerDto>> searchCustomers(
            @RequestParam(required = false) String customerNm,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate contractDate
    ) {
        List<CustomerDto> customers = customerService.searchCustomers(customerNm, contractDate);
        return ResponseEntity.ok(customers);
    }

    // =========================
    // 상세 조회 (ID 기준)
    // =========================
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable String customerId) {
        CustomerDto customer = customerService.getCustomer(customerId);
        return ResponseEntity.ok(customer);
    }

    // =========================
    // 등록 / 수정 (단건)
    // - 참고: PUT을 수정 전용으로 분리할 수도 있습니다.
    // =========================
    @PostMapping
    public ResponseEntity<CustomerDto> saveCustomer(@RequestBody CustomerDto customerDto) {
        CustomerDto savedCustomer = customerService.saveCustomer(customerDto);
        return ResponseEntity.ok(savedCustomer);
    }

    // =========================
    // 등록 / 수정 (여러 건)
    // =========================
    @PostMapping("/bulk")
    public ResponseEntity<List<CustomerDto>> saveAllCustomers(@RequestBody List<CustomerDto> customers) {
        List<CustomerDto> savedCustomers = customerService.saveAllCustomers(customers);
        return ResponseEntity.ok(savedCustomers);
    }

    // =========================
    // 삭제
    // =========================
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build(); // 성공적으로 삭제되었음을 의미
    }
}