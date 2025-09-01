package com.smartfactory.erp.controller;

import com.smartfactory.erp.dto.VesselDto;
import com.smartfactory.erp.service.VesselService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/vessels")
@RequiredArgsConstructor
public class VesselController {

    private final VesselService vesselService;

    @GetMapping
    public List<VesselDto> getSuppliers(){
        return vesselService.getAllSearch();
    }
}
