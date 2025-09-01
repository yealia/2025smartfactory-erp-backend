package com.smartfactory.erp.service;

import com.smartfactory.erp.dto.VesselDto;
import com.smartfactory.erp.entity.VesselEntity;
import com.smartfactory.erp.repository.VesselRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VesselService {
    private final VesselRepository vesselRepository;

    public List<VesselDto> getAllSearch(){
        List<VesselEntity> vesselEntityList = vesselRepository.findAll();
        return vesselEntityList.stream().map(VesselDto::fromEntity).toList();
    }
}
