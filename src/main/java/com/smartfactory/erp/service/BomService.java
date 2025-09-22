package com.smartfactory.erp.service;

import com.smartfactory.erp.dto.BomDto; // DTO를 직접 반환하도록 수정
import com.smartfactory.erp.entity.BomEntity;
import com.smartfactory.erp.repository.BomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 추가

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 읽기 전용 트랜잭션 추가
public class BomService {

    private final BomRepository bomRepository;

    /**
     * 동적 조회: 선박 ID로 BOM 조회
     * @param vesselId (선택) 검색할 선박 ID
     * @return DTO 리스트
     */
    public List<BomDto> searchBomsByVesselId(String vesselId) {
        Specification<BomEntity> spec = (root, query, cb) -> {
            if (vesselId != null && !vesselId.trim().isEmpty()) {
                // ✅ [수정] cb.equal을 cb.like로 변경
                return cb.like(root.get("vessel").get("vesselId"), "%" + vesselId + "%");
            }
            return null;
        };

        List<BomEntity> entities = bomRepository.findAll(spec);

        return entities.stream()
                .map(BomDto::fromEntity)
                .collect(Collectors.toList());
    }
}