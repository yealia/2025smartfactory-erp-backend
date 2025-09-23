package com.smartfactory.erp.service;

import com.smartfactory.erp.dto.InventoryDto;
import com.smartfactory.erp.entity.InventoryEntity;
import com.smartfactory.erp.entity.MaterialEntity;
import com.smartfactory.erp.entity.MovementEntity;
import com.smartfactory.erp.repository.InventoryRepository;
import com.smartfactory.erp.repository.MaterialRepository; // ✅ 자재 관계 설정을 위해 주입
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final MaterialRepository materialRepository; // ✅ MaterialRepository 주입

    /**
     * 동적 쿼리를 사용한 재고 검색
     */
    public List<InventoryDto> searchInventories(String inventoryId, Integer materialId) {
        Specification<InventoryEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (inventoryId != null && !inventoryId.trim().isEmpty()) {
                predicates.add(cb.like(root.get("inventoryId"), "%" + inventoryId + "%"));
            }
            if (materialId != null) {
                // Join을 통해 연관된 Material의 ID로 검색
                predicates.add(cb.equal(root.get("material").get("materialId"), materialId));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return inventoryRepository.findAll(spec).stream()
                .map(InventoryDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * ID로 단일 재고 조회
     */
    public InventoryDto getInventoryById(String inventoryId) {
        return inventoryRepository.findById(inventoryId)
                .map(InventoryDto::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 재고를 찾을 수 없습니다: " + inventoryId));
    }

    /**
     * 신규 재고 생성 또는 기존 재고 수정
     */
    @Transactional
    public InventoryDto createInventory(InventoryDto inventoryDto) {
        Integer materialId = inventoryDto.getMaterialId();
        if (materialId == null) {
            throw new IllegalArgumentException("자재 ID는 필수입니다.");
        }
        MaterialEntity material = materialRepository.findById(materialId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 자재 ID입니다: " + materialId));

        InventoryEntity inventoryEntity = inventoryDto.toEntity();
        inventoryEntity.setInventoryId(generateInventoryId()); // Always generate a new ID
        inventoryEntity.setMaterial(material);

        InventoryEntity savedEntity = inventoryRepository.save(inventoryEntity);
        return InventoryDto.fromEntity(savedEntity);
    }

    /**
     * 기존 재고 수정
     */
    @Transactional
    public InventoryDto updateInventory(String inventoryId, InventoryDto inventoryDto) {
        // 1. 기존 엔티티를 찾아서 없으면 예외 발생
        InventoryEntity existingEntity = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 재고를 찾을 수 없습니다: " + inventoryId));

        // 2. DTO의 변경사항을 기존 엔티티에 반영
        if (inventoryDto.getMaterialId() != null) {
            MaterialEntity material = materialRepository.findById(inventoryDto.getMaterialId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 자재 ID입니다: " + inventoryDto.getMaterialId()));
            existingEntity.setMaterial(material);
        }

        // 개별 필드 업데이트 (null 체크 후 반영)
        if (inventoryDto.getWarehouse() != null) existingEntity.setWarehouse(inventoryDto.getWarehouse());
        if (inventoryDto.getLocation() != null) existingEntity.setLocation(inventoryDto.getLocation());
        if (inventoryDto.getOnHand() != null) existingEntity.setOnHand(inventoryDto.getOnHand());
        if (inventoryDto.getReservedQty() != null) existingEntity.setReservedQty(inventoryDto.getReservedQty());
        if (inventoryDto.getSafetyStock() != null) existingEntity.setSafetyStock(inventoryDto.getSafetyStock());
        if (inventoryDto.getReorderPoint() != null) existingEntity.setReorderPoint(inventoryDto.getReorderPoint());
        if (inventoryDto.getRemark() != null) existingEntity.setRemark(inventoryDto.getRemark());

        // 3. 변경된 엔티티 저장 (JPA는 ID가 존재하면 UPDATE 쿼리 자동 생성)
        InventoryEntity updatedEntity = inventoryRepository.save(existingEntity);
        return InventoryDto.fromEntity(updatedEntity);
    }


    /**
     * 재고 삭제
     */
    @Transactional
    public void deleteInventory(String inventoryId) {
        if (!inventoryRepository.existsById(inventoryId)) {
            throw new IllegalArgumentException("삭제할 재고가 존재하지 않습니다: " + inventoryId);
        }
        inventoryRepository.deleteById(inventoryId);
    }

    // --- 기존 로직 (내부 재고 이동 처리용) ---
    private String generateInventoryId() {
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String prefix = "IN" + date;
        String lastId = inventoryRepository
                .findTopByInventoryIdStartingWithOrderByInventoryIdDesc(prefix)
                .map(InventoryEntity::getInventoryId)
                .orElse(prefix + "000");
        int next = Integer.parseInt(lastId.substring(prefix.length())) + 1;
        return prefix + String.format("%03d", next);
    }

    /** 원장 반영: PASS/REJECT/내부이동 처리 */
    @Transactional(propagation = Propagation.MANDATORY)
    public void applyMovement(MovementEntity m) {
        if (m == null) return;

        String type = m.getMovementType();
        if (type == null) return;

        if (type.startsWith("RECEIPT_QC_PASS")) {
            // PASS 입고: 도착지 +qty
            upsertOnHand(m.getMaterialId(), m.getWarehouseTo(), m.getLocationTo(), +m.getQty());
        } else if (type.startsWith("RECEIPT_QC_REJECT")) {
            // 불합격은 재고 반영 안 함(원장만 기록)
            return;
        } else if (type.equals("TRANSFER_INTERNAL")) {
            // 내부이동: 출발지 -qty, 도착지 +qty
            updateOnHand(m.getMaterialId(), m.getWarehouseFrom(), m.getLocationFrom(), -m.getQty());
            upsertOnHand(m.getMaterialId(), m.getWarehouseTo(), m.getLocationTo(), +m.getQty());
        }
    }

    private void upsertOnHand(Integer materialId, String wh, String loc, int delta) {
        InventoryEntity inv = inventoryRepository
                .findByMaterialIdAndWarehouseAndLocation(materialId, wh, loc)
                .orElseGet(() -> {
                    // 신규 재고 생성 시 Material 관계 설정
                    MaterialEntity material = materialRepository.findById(materialId)
                            .orElseThrow(() -> new IllegalArgumentException("자재 ID를 찾을 수 없습니다: " + materialId));

                    InventoryEntity e = new InventoryEntity();
                    e.setInventoryId(generateInventoryId());
                    e.setMaterial(material); // ✅ 관계 설정 추가
                    e.setWarehouse(wh);
                    e.setLocation(loc);
                    e.setOnHand(0);
                    e.setReservedQty(0);
                    e.setSafetyStock(0);
                    e.setReorderPoint(0);
                    return e;
                });
        inv.setOnHand(inv.getOnHand() + delta);
        if (inv.getOnHand() < 0) {
            throw new IllegalStateException("재고 수량이 0 미만이 될 수 없습니다.");
        }
        inventoryRepository.save(inv);
    }

    private void updateOnHand(Integer materialId, String wh, String loc, int delta) {
        InventoryEntity inv = inventoryRepository
                .findByMaterialIdAndWarehouseAndLocation(materialId, wh, loc)
                .orElseThrow(() -> new IllegalArgumentException("해당 위치의 재고가 없습니다."));
        inv.setOnHand(inv.getOnHand() + delta);
        if (inv.getOnHand() < 0) {
            throw new IllegalStateException("재고 수량이 0 미만이 될 수 없습니다.");
        }
        inventoryRepository.save(inv);
    }
}

