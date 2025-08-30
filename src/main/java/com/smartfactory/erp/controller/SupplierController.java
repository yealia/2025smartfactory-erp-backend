package com.smartfactory.erp.controller;

import com.smartfactory.erp.dto.SupplierDto;
import com.smartfactory.erp.service.SupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;

@Slf4j
@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private  final SupplierService supplierService;

    @GetMapping
    public List<SupplierDto> getSuppliers(String supplierNm, LocalDate contractDate) {
        if (supplierNm != null && contractDate != null) {
            return supplierService.getAllSearchSupplierContracDate(supplierNm, contractDate);
        } else if (supplierNm != null) {
            return supplierService.getAllSearchSupplier(supplierNm);
        } else if (contractDate != null) {
            return supplierService.getAllSearchcontractDate(contractDate);
        } else {
            return supplierService.getAllSearch();
        }
    }
    @GetMapping("/{supplierId}")
    public SupplierDto getSupplier(@PathVariable Integer supplierId){
        return supplierService.getSearch(supplierId);
    }
    @PostMapping("")
    public SupplierDto saveSupplier(@RequestBody SupplierDto supplier){
        return supplierService.saveSupplier(supplier);
    }
    @PostMapping("/saveAll")
    public List<SupplierDto> saveSupplier(@RequestBody List<SupplierDto> suppliers){
        return supplierService.saveAllSupplier(suppliers);
    }
    @DeleteMapping("/{supplierId}")
    public void deleteSupplier(@PathVariable("supplierId") Integer supplierId) {
        System.out.println("삭제 요청 들어온 ID = " + supplierId);
        log.info("*****={}",supplierId);
        supplierService.deleteSupplier(supplierId);
    }

}
