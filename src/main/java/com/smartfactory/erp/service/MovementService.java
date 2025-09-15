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

    public List<MovementDto> findAll(){
        return movementRepository.findAll()
                .stream()
                .map(MovementDto::fromEntity)
                .toList();
    }

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
}
