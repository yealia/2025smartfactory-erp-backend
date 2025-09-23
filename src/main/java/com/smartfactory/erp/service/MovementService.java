package com.smartfactory.erp.service;

import com.smartfactory.erp.dto.MovementDto;
import com.smartfactory.erp.entity.MovementEntity;
import com.smartfactory.erp.repository.MovementRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovementService {

    private final MovementRepository movementRepository;
    private final InventoryService inventoryService;

    @Transactional
    public List<MovementDto> saveMovements(List<MovementDto> movementDtos){
        List<MovementEntity> toSave = new ArrayList<>();

        for (MovementDto dto : movementDtos) {
            MovementEntity e = dto.toEntity();
            if (e.getUserId() == null || e.getUserId().isBlank()) {
                e.setUserId("yelia"); // 기본 사용자 ID 설정
            }
            toSave.add(e);
        }

        List<MovementEntity> saved = movementRepository.saveAll(toSave);

        // 같은 트랜잭션 안에서 재고 반영
        saved.forEach(inventoryService::applyMovement);

        return saved.stream().map(MovementDto::fromEntity).collect(Collectors.toList());
    }

    /**
     * 동적 조건으로 재고 이력 조회
     * @param movementId 이력 ID (선택)
     * @param materialId 자재 ID (선택)
     * @return 조회된 재고 이력 DTO 리스트
     */
    @Transactional(readOnly = true)
    public List<MovementDto> searchMovements(Integer movementId, Integer materialId) {
        // Specification을 사용하여 동적 쿼리 생성
        Specification<MovementEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (movementId != null) {
                predicates.add(cb.equal(root.get("movementId"), movementId));
            }
            if (materialId != null) {
                // 연관된 Material 엔티티의 ID로 검색
                predicates.add(cb.equal(root.get("material").get("materialId"), materialId));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return movementRepository.findAll(spec).stream()
                .map(MovementDto::fromEntity)
                .collect(Collectors.toList());
    }
}