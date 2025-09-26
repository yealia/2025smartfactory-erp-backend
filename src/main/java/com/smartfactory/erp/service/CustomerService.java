// CustomerService.java

package com.smartfactory.erp.service;

import com.smartfactory.erp.dto.CustomerDto; // DTO는 아래에 생성합니다.
import com.smartfactory.erp.entity.CustomerEntity;
import com.smartfactory.erp.repository.CustomerRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;

    // =========================
    // 동적 조건 조회
    // =========================
    public List<CustomerDto> searchCustomers(String customerNm, LocalDate contractDate) {
        // Specification을 사용하여 동적 쿼리 생성
        Specification<CustomerEntity> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 고객명(customerNm) 조건: like 검색
            if (customerNm != null && !customerNm.trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("customerNm"), "%" + customerNm + "%"));
            }

            // 계약일(contractDate) 조건: 일치 검색
            if (contractDate != null) {
                predicates.add(criteriaBuilder.equal(root.get("contractDate"), contractDate));
            }

            // 모든 조건을 AND로 연결
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        List<CustomerEntity> entities = customerRepository.findAll(spec);
        return entities.stream()
                .map(CustomerDto::fromEntity) // Entity -> DTO 변환
                .collect(Collectors.toList());
    }

    // =========================
    // 단건 조회 (ID 기준)
    // =========================
    public CustomerDto getCustomer(String customerId) {
        return customerRepository.findById(customerId)
                .map(CustomerDto::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("해당 고객을 찾을 수 없습니다: " + customerId));
    }

    // =========================
    // 등록 / 수정
    // =========================
    @Transactional
    public CustomerDto saveCustomer(CustomerDto customerDto) {
        CustomerEntity entity = customerDto.toEntity();
        CustomerEntity savedEntity = customerRepository.save(entity);
        return CustomerDto.fromEntity(savedEntity);
    }

    @Transactional
    public List<CustomerDto> saveAllCustomers(List<CustomerDto> customers) {
        List<CustomerEntity> entities = customers.stream()
                .map(CustomerDto::toEntity)
                .collect(Collectors.toList());
        List<CustomerEntity> savedEntities = customerRepository.saveAll(entities);
        return savedEntities.stream()
                .map(CustomerDto::fromEntity)
                .collect(Collectors.toList());
    }

    // =========================
    // 삭제
    // =========================
    @Transactional
    public void deleteCustomer(String customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new IllegalArgumentException("삭제할 고객이 존재하지 않습니다: " + customerId);
        }
        customerRepository.deleteById(customerId);
    }
}