package com.smartfactory.erp.controller;

import com.smartfactory.erp.dto.BomDto;
import com.smartfactory.erp.service.BomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boms")
@RequiredArgsConstructor
public class BomController {

    private final BomService bomService;

    @GetMapping
    public List<BomDto> getBoms(@RequestParam(required = false) String vesselId) {
        // ✅ [수정] 서비스에서 DTO를 바로 반환받아 코드가 간결해짐
        return bomService.searchBomsByVesselId(vesselId);
    }
}