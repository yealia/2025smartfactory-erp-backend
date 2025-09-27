package com.smartfactory.erp.service;

import com.smartfactory.erp.dto.InventoryDto;
import com.smartfactory.erp.dto.QualityControlDto;
import com.smartfactory.erp.dto.SimpleStockRequestDto;
import com.smartfactory.erp.dto.StockRequestDto;
import com.smartfactory.erp.entity.MovementEntity;
import com.smartfactory.erp.entity.InventoryEntity;
import com.smartfactory.erp.entity.MaterialEntity;
import com.smartfactory.erp.repository.InventoryRepository;
import com.smartfactory.erp.repository.MaterialRepository; // ✅ 자재 관계 설정을 위해 주입
import com.smartfactory.erp.repository.MovementRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final MaterialRepository materialRepository; // MaterialRepository 주입
    private final MovementRepository movementRepository;

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
                    e.setMaterial(material); // 관계 설정 추가
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

    // 3. 자재 ID만 있을 경우
    public List<InventoryDto> findByMaterialId(Integer materialId) {
        return inventoryRepository.findByMaterialId(materialId).stream()
                .map(InventoryDto::fromEntity)
                .toList();
    }

    // 4. 두 조건이 모두 있을 경우
    public List<InventoryDto> findByInventoryIdAndMaterialId(String inventoryId, Integer materialId) {
        return inventoryRepository.findByInventoryIdContainingAndMaterialId(inventoryId, materialId).stream()
                .map(InventoryDto::fromEntity)
                .toList();
    }

    // =================  메서드 =================
    // MES ↔ ERP 연동 시 사용할 API, StockRequest DTO 기반

    /** (운영) 재고 차감 */
    @Transactional
    public void deductStock(StockRequestDto req) {
        InventoryEntity inv = inventoryRepository
                .findByMaterialIdAndWarehouseAndLocation(req.getMaterialId(), req.getWarehouse(), req.getLocation())
                .orElseThrow(() -> new IllegalArgumentException("재고 없음. materialId=" + req.getMaterialId()));
        if (inv.getOnHand() < req.getQuantity()) {
            throw new IllegalStateException("재고 부족: 현재=" + inv.getOnHand() + ", 요청=" + req.getQuantity());
        }
        inv.setOnHand(inv.getOnHand() - req.getQuantity());
        inventoryRepository.save(inv);
    }

//    /** (운영) 재고 수정 */
//    @Transactional
//    public void updateStock(StockRequestDto req) {
//        InventoryEntity inv = inventoryRepository
//                .findByMaterialIdAndWarehouseAndLocation(req.getMaterialId(), req.getWarehouse(), req.getLocation())
//                .orElseThrow(() -> new IllegalArgumentException("재고 없음. materialId=" + req.getMaterialId()));
//        inv.setOnHand(req.getQuantity());
//        inventoryRepository.save(inv);
//    }

    /** (운영) 재고 복구 */
    @Transactional
    public void restoreStock(StockRequestDto req) {
        InventoryEntity inv = inventoryRepository
                .findByMaterialIdAndWarehouseAndLocation(req.getMaterialId(), req.getWarehouse(), req.getLocation())
                .orElseThrow(() -> new IllegalArgumentException("재고 없음. materialId=" + req.getMaterialId()));
        inv.setOnHand(inv.getOnHand() + req.getQuantity());
        inventoryRepository.save(inv);
    }

    @Transactional
    public void restoreStockByMaterialId(SimpleStockRequestDto dto) {
        // 자재 ID로 재고 목록을 찾음 (하나만 있다고 가정)
        List<InventoryEntity> inventories = inventoryRepository.findByMaterialId(dto.getMaterialId());
        if (!inventories.isEmpty()) {
            InventoryEntity inventoryToUpdate = inventories.get(0);
            inventoryToUpdate.setOnHand(inventoryToUpdate.getOnHand() - dto.getQuantity()); // 복구는 덧셈
            inventoryRepository.save(inventoryToUpdate);
        } else {
            log.warn("재고 복구 대상 없음. Material ID: {}", dto.getMaterialId());
        }
    }

    @Transactional
    public void processQcResult(QualityControlDto dto) {
        boolean alreadyProcessed = movementRepository.existsByQcId(dto.getQcId());

        // 이미 처리된 데이터라면, 로그만 남기고 아무것도 하지 않고 넘어갑니다.
        if (alreadyProcessed) {
            log.info("이미 처리된 QC 결과입니다. 건너뜁니다. (QC ID: {})", dto.getQcId());
            return;
        }

        // --- 아래는 아직 처리되지 않은 신규 데이터에 대한 기존 로직 ---

        // 1. 재고(inventory) 업데이트 (자재 ID로만 찾아 업데이트)
        if (dto.getPassQuantity() > 0) {
            List<InventoryEntity> inventories = inventoryRepository.findByMaterialId(dto.getMaterialId());
            if (!inventories.isEmpty()) {
                InventoryEntity inventoryToUpdate = inventories.get(0);
                inventoryToUpdate.setOnHand(inventoryToUpdate.getOnHand() + dto.getPassQuantity());
                inventoryRepository.save(inventoryToUpdate);
            } else {
                log.warn("MES 동기화 중 재고 정보를 찾을 수 없습니다: Material ID {}", dto.getMaterialId());
            }
        }

        // 2. 이동(movements) 테이블에 합격/불합격 모두 기록
        // --- 재고 이동(movements) 테이블에 결과 기록 ---
        MovementEntity movement = new MovementEntity();

        // 1. 관계 설정 및 기본 정보 입력
        // materialId를 직접 설정하는 대신, MaterialEntity를 찾아 관계를 설정합니다.
        MaterialEntity material = materialRepository.findById(dto.getMaterialId())
                .orElseThrow(() -> new IllegalArgumentException("자재 정보를 찾을 수 없습니다: " + dto.getMaterialId()));
        movement.setMaterial(material);

        // 기타 필요한 정보 설정
        movement.setOccurredAt(LocalDateTime.now());
        movement.setMovementType("QC_COMPLETE");
        movement.setSourceType("MES_QC");
        movement.setQcId(dto.getQcId());
        movement.setPurchaseOrderId(dto.getPurchaseOrderId());
        movement.setOrderDetailId(dto.getOrderDetailId());
        movement.setUserId(dto.getInspectorId()); // 검사자를 기록


        // 2. qty 필드에 합격 수량과 불합격 수량을 더한 총량을 저장
        movement.setQty(dto.getPassQuantity() + dto.getFailQuantity());

        // 3. remark 필드에 상세 내역을 기록하여 추적 용이성을 높임
        movement.setRemark(String.format("QC 결과: 합격 %d, 불합격 %d", dto.getPassQuantity(), dto.getFailQuantity()));
        movement.setIdempotencyKey("qc-result-" + dto.getQcId());

        // pass/fail 수량에 따라 movementType 결정 ★★★
        if (dto.getPassQuantity() > 0 && dto.getFailQuantity() == 0) {
            movement.setMovementType("RECEIPT_QC_PASS"); // 전체 합격 (React 코드의 "입고" 조건과 유사하게)
        } else if (dto.getPassQuantity() == 0 && dto.getFailQuantity() > 0) {
            movement.setMovementType("ISSUE_QC_FAIL");   // 전체 불합격 (React 코드의 "출고" 조건과 유사하게)
        } else if (dto.getPassQuantity() > 0 && dto.getFailQuantity() > 0){
            movement.setMovementType("QC_PARTIAL");      // 부분 합격
        } else {
            movement.setMovementType("QC_COMPLETE");     // 수량이 0인 경우 등 기타
        }

        movementRepository.save(movement);
    }
}


