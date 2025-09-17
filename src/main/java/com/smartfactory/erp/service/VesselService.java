package com.smartfactory.erp.service;

import com.smartfactory.erp.dto.VesselDto;
import com.smartfactory.erp.repository.VesselRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VesselService {

    private final VesselRepository vesselRepository;

    // 1. 조건이 둘 다 없을 경우
    public List<VesselDto> findAll() {
        return vesselRepository.findAll().stream()
                .map(VesselDto::fromEntity)
                .toList();
    }

    // 2. 선박명만 있을 경우
    public List<VesselDto> findByVesselName(String vesselNm) {
        return vesselRepository.findByVesselNmContaining(vesselNm).stream()
                .map(VesselDto::fromEntity)
                .toList();
    }

    // 3. 선박 ID만 있을 경우
    public List<VesselDto> findByVesselId(String vesselId) {
        return vesselRepository.findByVesselIdContaining(vesselId).stream()
                .map(VesselDto::fromEntity)
                .toList();
    }

    // 4. 두 조건이 모두 있을 경우
    public List<VesselDto> findByVesselIdAndName(String vesselId, String vesselNm) {
        return vesselRepository.findByVesselIdContainingAndVesselNmContaining(vesselId, vesselNm).stream()
                .map(VesselDto::fromEntity)
                .toList();
    }
}