package com.smartfactory.erp.service;

import com.smartfactory.erp.dto.SalesOrderDto;
import com.smartfactory.erp.repository.SalesOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesOrderService {

    private final SalesOrderRepository repository;

    public List<SalesOrderDto> findAll() {
        return repository.findAll()
                .stream()
                .map(SalesOrderDto::fromEntity)
                .toList();
    }

    public List<SalesOrderDto> findByCustomer(String customerId) {
        return repository.findByCustomerIdContaining(customerId)
                .stream()
                .map(SalesOrderDto::fromEntity)
                .toList();
    }

    public List<SalesOrderDto> findByVessel(String vesselId) {
        return repository.findByVesselIdContaining(vesselId)
                .stream()
                .map(SalesOrderDto::fromEntity)
                .toList();
    }

    public List<SalesOrderDto> findByCustomerAndVessel(String customerId, String vesselId) {
        return repository.findByCustomerIdContainingAndVesselIdContaining(customerId, vesselId)
                .stream()
                .map(SalesOrderDto::fromEntity)
                .toList();
    }
}