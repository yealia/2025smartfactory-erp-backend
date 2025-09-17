package com.smartfactory.erp.service;

import com.smartfactory.erp.dto.MovementDto;
import com.smartfactory.erp.entity.MovementEntity;
import com.smartfactory.erp.repository.MovementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovementService {

    private final MovementRepository movementRepository;
    private final InventoryService inventoryService; // ← 여기서 심볼 인식되어야 함

    @Transactional
    public List<MovementDto> saveMovements(List<MovementDto> movementDtos){
        List<MovementEntity> toSave = new ArrayList<>();

        for (MovementDto dto : movementDtos) {
            MovementEntity e = dto.toEntity();
            if (e.getUserId() == null || e.getUserId().isBlank()) {
                e.setUserId("yelia");
            }
            toSave.add(e);
        }

        List<MovementEntity> saved = movementRepository.saveAll(toSave);

        // 같은 트랜잭션 안에서 재고 반영
        saved.forEach(inventoryService::applyMovement);

        return saved.stream().map(MovementDto::fromEntity).toList();
    }

    // 1. 조건이 둘 다 없을 경우 (전체 조회)
    public List<MovementDto> findAll() {
        return movementRepository.findAll().stream()
                .map(MovementDto::fromEntity)
                .toList();
    }

    // 2. 이력 ID만 있을 경우
    public List<MovementDto> findByMovementId(Integer movementId) {
        // findById는 Optional을 반환하므로, 리스트로 변환하는 처리가 필요
        return movementRepository.findById(movementId)
                .map(entity -> List.of(MovementDto.fromEntity(entity)))
                .orElse(List.of()); // 결과가 없으면 빈 리스트 반환
    }

    // 3. 자재 ID만 있을 경우
    public List<MovementDto> findByMaterialId(Integer materialId) {
        return movementRepository.findByMaterialId(materialId).stream()
                .map(MovementDto::fromEntity)
                .toList();
    }

    // 4. 두 조건이 모두 있을 경우
    public List<MovementDto> findByMovementIdAndMaterialId(Integer movementId, Integer materialId) {
        return movementRepository.findByMovementIdAndMaterialId(movementId, materialId).stream()
                .map(MovementDto::fromEntity)
                .toList();
    }
}
