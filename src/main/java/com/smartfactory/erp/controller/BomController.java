package com.smartfactory.erp.controller;

import com.smartfactory.erp.dto.BomDto;
import com.smartfactory.erp.entity.BomEntity;
import com.smartfactory.erp.service.BomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController //컨트롤러
@RequestMapping("/api/boms") //기본 URL지정
@RequiredArgsConstructor
public class BomController {

    private final BomService bomService;

    @GetMapping
    public List<BomDto> getCustomers(){
        return bomService.getAllSearch();
    }
}
