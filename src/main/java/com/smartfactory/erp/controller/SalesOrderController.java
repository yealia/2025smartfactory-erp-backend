package com.smartfactory.erp.controller;

import com.smartfactory.erp.dto.SalesOrderDto;
import com.smartfactory.erp.service.SalesOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sales_orders")
@RequiredArgsConstructor
public class SalesOrderController {

    private final SalesOrderService service;

    @GetMapping
    public List<SalesOrderDto> getSalesOrders(String customerId, String vesselId) {
        if (customerId != null && vesselId != null) {
            return service.findByCustomerAndVessel(customerId, vesselId);
        } else if (customerId != null) {
            return service.findByCustomer(customerId);
        } else if (vesselId != null) {
            return service.findByVessel(vesselId);
        } else {
            return service.findAll();
        }
    }
}