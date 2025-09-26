package com.smartfactory.erp.service;

import com.smartfactory.erp.dto.PositionDto;
import com.smartfactory.erp.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;

    // 모든 직책을 조회하여 DTO 리스트로 변환 후 반환하는 메서드
    public List<PositionDto> findAll() {
        return positionRepository.findAll().stream()
                .map(PositionDto::fromEntity) // PositionEntity를 PositionDto로 변환
                .collect(Collectors.toList());
    }
}
