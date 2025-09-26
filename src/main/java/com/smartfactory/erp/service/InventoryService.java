package com.smartfactory.erp.service;

import com.smartfactory.erp.dto.InventoryDto;
import com.smartfactory.erp.dto.StockRequestDto;
import com.smartfactory.erp.entity.MovementEntity;
import com.smartfactory.erp.entity.InventoryEntity;
import com.smartfactory.erp.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

//    public List<InventoryDto> findAll(){
//        return inventoryRepository.findAll()
//                .stream()
//                .map(InventoryDto::fromEntity)
//                .toList();
//    }

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
                    InventoryEntity e = new InventoryEntity();
                    e.setInventoryId(generateInventoryId()); // @PrePersist 또는 서비스에서 채번한다면 거기서 설정
                    e.setMaterialId(materialId);
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

    // 1. 조건이 둘 다 없을 경우 (전체 조회)
    public List<InventoryDto> findAll() {
        return inventoryRepository.findAll().stream()
                .map(InventoryDto::fromEntity)
                .toList();
    }

    // 2. 재고 ID만 있을 경우
    public List<InventoryDto> findByInventoryId(String inventoryId) {
        return inventoryRepository.findByInventoryIdContaining(inventoryId).stream()
                .map(InventoryDto::fromEntity)
                .toList();
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
}
