package com.smartfactory.erp.service;

import com.smartfactory.erp.dto.BomDto;
import com.smartfactory.erp.entity.BomEntity;
import com.smartfactory.erp.repository.BomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BomService {
    //레파지토리 선언
    private final BomRepository bomRepository;
    //전체 조회
    public List<BomDto> getAllSearch(){
        List<BomEntity> bomEntityList = bomRepository.findAll();
        return bomEntityList.stream()
                .map(BomDto::fromEntity)
                .toList();
    }
}
