package com.smartfactory.erp.service;

import com.smartfactory.erp.dto.CustomerDto;
import com.smartfactory.erp.dto.SupplierDto;
import com.smartfactory.erp.entity.SupplierEntity;
import com.smartfactory.erp.repository.SupplierRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;

    //조건 둘다 없을때
    public List<SupplierDto> getAllSearch(){
        return supplierRepository.findAll()
                .stream()
                .map(SupplierDto::fromEntity)
                .toList();
    }
    //고객명 조회
    public List<SupplierDto> getAllSearchSupplier(String SupplierNm){
        return supplierRepository.findBySupplierNmContaining(SupplierNm)
                .stream()
                .map(SupplierDto::fromEntity)
                .toList();
    }
    //날짜 조회
    public List<SupplierDto> getAllSearchcontractDate(LocalDate contractDate){
        return supplierRepository.findByContractDate(contractDate)
                .stream()
                .map(SupplierDto::fromEntity)
                .toList();
    }
    //조건 둘다 조회
    public List<SupplierDto> getAllSearchSupplierContracDate(String supplierNm, LocalDate contractDate){
        return supplierRepository.findBySupplierNmContainingAndContractDate(supplierNm, contractDate)
                .stream()
                .map(SupplierDto::fromEntity)
                .toList();
    }
    //상세조회
    public SupplierDto getSearch(Integer supplierId){
        return supplierRepository.findById(supplierId)
                .map(SupplierDto::fromEntity)
                .orElse(null);
    }

    //저장
    //디티오를 엔터티
    //레파에 보내고
    //다시 디티오로변환
    @Transactional
    public SupplierDto saveSupplier(SupplierDto supplier){
        SupplierEntity saveSupplier =supplierRepository.save(supplier.toEntity());
        return SupplierDto.fromEntity(saveSupplier);
    }
    //모두저장
    @Transactional
    public List<SupplierDto> saveAllSupplier(List<SupplierDto> suppliers){
        List<SupplierEntity> suppliersEntity = suppliers.stream()
                                                        .map(SupplierDto::toEntity)
                                                        .toList();
        List<SupplierEntity> saveAll = supplierRepository.saveAll(suppliersEntity);
        return saveAll.stream().map(SupplierDto::fromEntity).toList();
    }
    //삭제
    @Transactional
    public void deleteSupplier(Integer supplierId){
        supplierRepository.deleteById(supplierId);
    }

}
