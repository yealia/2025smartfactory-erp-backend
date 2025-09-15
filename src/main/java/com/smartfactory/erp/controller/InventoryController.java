package com.smartfactory.erp.controller;

import com.smartfactory.erp.dto.InventoryDto;
import com.smartfactory.erp.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController //컨트롤러
@RequestMapping("/api/inventory") //기본 URL지정
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public List<InventoryDto> getInventory(){
        return inventoryService.findAll();
    }
}
