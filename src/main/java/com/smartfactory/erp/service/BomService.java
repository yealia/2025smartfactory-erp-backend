package com.smartfactory.erp.service;

import com.smartfactory.erp.dto.BomDto;
import com.smartfactory.erp.entity.*;
import com.smartfactory.erp.repository.BomRepository;
import com.smartfactory.erp.repository.MaterialRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 클래스 레벨에서 읽기 전용으로 설정
public class BomService {

    private final BomRepository bomRepository;
    private final MaterialRepository materialRepository;

    /**
     * BOM 생성 (Create)
     */
    @Transactional // 쓰기 작업이므로 readOnly=false 적용
    public BomDto createBom(BomDto bomDto) {
        BomEntity bomEntity = bomDto.toEntity();

        // ✨ [수정된 부분] 자재명으로 실제 자재 엔티티를 찾아서 연결합니다.
        if (bomDto.getMaterialName() != null && !bomDto.getMaterialName().trim().isEmpty()) {
            MaterialEntity material = materialRepository.findByMaterialNm(bomDto.getMaterialName())
                    .orElseThrow(() -> new EntityNotFoundException("Material not found with name: " + bomDto.getMaterialName()));
            bomEntity.setMaterial(material);
        }
        // (Vessel, Process, Block도 이름 등으로 찾아야 한다면 여기에 동일한 로직 추가)

        BomEntity savedEntity = bomRepository.save(bomEntity);
        return BomDto.fromEntity(savedEntity);
    }


    /**
     * BOM 단일 조회 (Read by ID)
     */
    public BomDto getBomById(Integer id) {
        BomEntity bomEntity = bomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BOM not found with id: " + id));
        return BomDto.fromEntity(bomEntity);
    }

    /**
     * 동적 조회: 선박 ID로 BOM 조회 (Search)
     */
    public List<BomDto> searchBomsByVesselId(String vesselId) {
        Specification<BomEntity> spec = (root, query, cb) -> {
            if (vesselId != null && !vesselId.trim().isEmpty()) {
                return cb.like(root.get("vessel").get("vesselId"), "%" + vesselId + "%");
            }
            return null; // 조건이 없으면 모든 BOM을 반환
        };

        List<BomEntity> entities = bomRepository.findAll(spec);

        return entities.stream()
                .map(BomDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * BOM 수정 (Update)
     */
    @Transactional // 쓰기 작업
    public BomDto updateBom(Integer id, BomDto bomDto) {
        // 1. ID로 기존 엔티티 조회
        BomEntity existingBom = bomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BOM not found with id: " + id));

        // 2. DTO의 정보로 엔티티 필드 업데이트
        existingBom.setRequiredQuantity(bomDto.getRequiredQuantity());
        existingBom.setUnit(bomDto.getUnit());
        existingBom.setRemark(bomDto.getRemark());

        // 3. 연관관계 업데이트 (ID가 변경된 경우에만)
        if (bomDto.getVesselId() != null) {
            VesselEntity vessel = new VesselEntity();
            vessel.setVesselId(bomDto.getVesselId());
            existingBom.setVessel(vessel);
        }
        if (bomDto.getProcessId() != null) {
            ProcessEntity process = new ProcessEntity();
            process.setProcessId(bomDto.getProcessId());
            existingBom.setProcess(process);
        }
        if (bomDto.getBlockId() != null) {
            BlockEntity block = new BlockEntity();
            block.setBlockId(bomDto.getBlockId());
            existingBom.setBlock(block);
        }
        if (bomDto.getMaterialName() != null && !bomDto.getMaterialName().trim().isEmpty()) {
            MaterialEntity material = materialRepository.findByMaterialNm(bomDto.getMaterialName())
                    .orElseThrow(() -> new EntityNotFoundException("Material not found with name: " + bomDto.getMaterialName()));
            existingBom.setMaterial(material);
        } else {
            // 만약 자재명을 비워서 보냈다면, 연관관계를 끊습니다.
            existingBom.setMaterial(null);
        }

        // 4. JPA의 더티 체킹(dirty checking)에 의해 트랜잭션 종료 시 자동으로 업데이트 쿼리 실행
        // 명시적으로 save를 호출할 필요는 없지만, 가독성을 위해 호출하기도 함.
        // BomEntity updatedEntity = bomRepository.save(existingBom);

        return BomDto.fromEntity(existingBom);
    }

    /**
     * BOM 삭제 (Delete)
     */
    @Transactional // 쓰기 작업
    public void deleteBom(Integer id) {
        if (!bomRepository.existsById(id)) {
            throw new EntityNotFoundException("BOM not found with id: " + id);
        }
        bomRepository.deleteById(id);
    }
}