package com.smartfactory.erp.controller;

import com.smartfactory.erp.dto.VesselDto;
import com.smartfactory.erp.service.VesselService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vessels")
@RequiredArgsConstructor
public class VesselController {

    private final VesselService vesselService;

    @GetMapping
    public List<VesselDto> getVessels(String vesselId, String vesselNm) {

        // 4. 두 조건이 모두 있는 경우
        if (vesselId != null && vesselNm != null) {
            return vesselService.findByVesselIdAndName(vesselId, vesselNm);
        }
        // 3. 선박 ID만 있는 경우
        else if (vesselId != null) {
            return vesselService.findByVesselId(vesselId);
        }
        // 2. 선박명만 있는 경우
        else if (vesselNm != null) {
            return vesselService.findByVesselName(vesselNm);
        }
        // 1. 아무 조건도 없는 경우
        else {
            return vesselService.findAll();
        }
    }
}