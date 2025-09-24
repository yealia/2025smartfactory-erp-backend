package com.smartfactory.erp.service;

import com.smartfactory.erp.dto.MovementDto;
import com.smartfactory.erp.entity.MaterialEntity;
import com.smartfactory.erp.entity.MovementEntity;
import com.smartfactory.erp.repository.MaterialRepository;
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
    private final MaterialRepository materialRepository; // ✅ 연관관계 설정을 위해 주입

    /**
     * [기존] 웹훅을 통한 여러 건 저장
     */
    @Transactional
    public List<MovementDto> saveMovementsFromWebhook(List<MovementDto> movementDtos) {
        List<MovementEntity> toSave = new ArrayList<>();

        for (MovementDto dto : movementDtos) {
            MovementEntity e = dto.toEntity();
            // 연관된 MaterialEntity 찾기
            MaterialEntity material = materialRepository.findById(dto.getMaterialId())
                    .orElseThrow(() -> new IllegalArgumentException("자재 ID를 찾을 수 없습니다: " + dto.getMaterialId()));
            e.setMaterial(material);

            if (e.getUserId() == null || e.getUserId().isBlank()) {
                e.setUserId("yelia"); // 기본 사용자 ID 설정
            }
            toSave.add(e);
        }

        List<MovementEntity> saved = movementRepository.saveAll(toSave);
        saved.forEach(inventoryService::applyMovement);
        return saved.stream().map(MovementDto::fromEntity).collect(Collectors.toList());
    }

    /**
     * ✅ [추가] 단일 재고 이력 생성 또는 수정
     */
    @Transactional
    public MovementDto saveMovement(MovementDto dto) {
        MaterialEntity material = materialRepository.findById(dto.getMaterialId())
                .orElseThrow(() -> new IllegalArgumentException("자재 ID를 찾을 수 없습니다: " + dto.getMaterialId()));

        MovementEntity entity = dto.toEntity();
        entity.setMaterial(material); // 연관관계 설정

        MovementEntity savedEntity = movementRepository.save(entity);

        // 여기에 재고 변경 로직이 필요하다면 추가 (예: inventoryService 호출)
        // inventoryService.applyMovement(savedEntity);

        return MovementDto.fromEntity(savedEntity);
    }

    /**
     * ✅ [추가] 단일 재고 이력 삭제
     */
    @Transactional
    public void deleteMovement(Integer movementId) {
        if (!movementRepository.existsById(movementId)) {
            throw new IllegalArgumentException("삭제할 재고 이력이 존재하지 않습니다: " + movementId);
        }
        // 참고: 재고 이력 삭제 시, 기존에 변경했던 재고를 원복시키는 로직이 필요할 수 있습니다.
        // 이 예제에서는 단순 삭제만 구현합니다.
        movementRepository.deleteById(movementId);
    }

    /**
     * 동적 조건으로 재고 이력 조회
     */
    @Transactional(readOnly = true)
    public List<MovementDto> searchMovements(Integer movementId, Integer materialId) {
        Specification<MovementEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (movementId != null) {
                predicates.add(cb.equal(root.get("movementId"), movementId));
            }
            if (materialId != null) {
                predicates.add(cb.equal(root.get("material").get("materialId"), materialId));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return movementRepository.findAll(spec).stream()
                .map(MovementDto::fromEntity)
                .collect(Collectors.toList());
    }
}