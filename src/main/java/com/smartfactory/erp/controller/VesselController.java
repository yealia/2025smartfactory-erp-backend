package com.smartfactory.erp.controller;

import com.smartfactory.erp.dto.VesselDto;
import com.smartfactory.erp.service.VesselService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/vessels")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class VesselController {

    private final VesselService vesselService;

    /**
     * 🔍 동적 검색 (선박ID, 선박명)
     */
    @GetMapping
    public List<VesselDto> searchVessels(
            @RequestParam(required = false) String vesselId,
            @RequestParam(required = false) String vesselNm) {
        return vesselService.searchVessels(vesselId, vesselNm);
    }

    /**
     * 👀 단건 조회
     */
    @GetMapping("/{vesselId}")
    public VesselDto getVessel(@PathVariable String vesselId) {
        return vesselService.getVesselById(vesselId);
    }

    /**
     * ➕ 단건 저장 (등록 & 수정)
     */
    @PostMapping
    public VesselDto saveVessel(@RequestBody VesselDto vesselDto) {
        return vesselService.saveVessel(vesselDto);
    }

    /**
     * 📦 여러 건 저장
     */
    @PostMapping("/saveAll")
    public List<VesselDto> saveAllVessels(@RequestBody List<VesselDto> vesselDtos) {
        return vesselService.saveAllVessels(vesselDtos);
    }

    /**
     * 📝 수정
     */
    @PutMapping("/{vesselId}")
    public VesselDto updateVessel(@PathVariable String vesselId, @RequestBody VesselDto vesselDto) {
        vesselDto.setVesselId(vesselId);
        return vesselService.saveVessel(vesselDto);
    }

    /**
     * 🗑️ 삭제
     */
    @DeleteMapping("/{vesselId}")
    public void deleteVessel(@PathVariable String vesselId) {
        log.info("삭제 요청 ID = {}", vesselId);
        vesselService.deleteVessel(vesselId);
    }
}
