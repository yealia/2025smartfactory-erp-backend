package com.smartfactory.erp.service;

import com.smartfactory.erp.dto.VesselDto;
import com.smartfactory.erp.entity.ProjectEntity;
import com.smartfactory.erp.entity.VesselEntity;
import com.smartfactory.erp.repository.ProjectRepository;
import com.smartfactory.erp.repository.VesselRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VesselService {

    private final VesselRepository vesselRepository;
    private final ProjectRepository projectRepository;

    /**
     * 동적 검색 (선박ID, 선박명)
     */
    public List<VesselDto> searchVessels(String vesselId, String vesselNm) {
        Specification<VesselEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(vesselId)) {
                predicates.add(cb.like(root.get("vesselId"), "%" + vesselId + "%"));
            }

            if (StringUtils.hasText(vesselNm)) {
                predicates.add(cb.like(root.get("vesselNm"), "%" + vesselNm + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return vesselRepository.findAll(spec).stream()
                .map(VesselDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 단건 조회
     */
    public VesselDto getVesselById(String vesselId) {
        return vesselRepository.findById(vesselId)
                .map(VesselDto::fromEntity)
                .orElse(null);
    }

    /**
     * 저장 (등록 & 수정)
     */
    @Transactional
    public VesselDto saveVessel(VesselDto vesselDto) {
        VesselEntity entity = vesselDto.toEntity();

        VesselEntity saved = vesselRepository.save(entity);
        return VesselDto.fromEntity(saved);
    }

    /**
     * 여러 건 저장
     */
    @Transactional
    public List<VesselDto> saveAllVessels(List<VesselDto> vesselDtos) {
        List<VesselEntity> entities = vesselDtos.stream()
                .map(dto -> {
                    VesselEntity entity = dto.toEntity();
                    return entity;
                })
                .toList();

        List<VesselEntity> savedEntities = vesselRepository.saveAll(entities);
        return savedEntities.stream()
                .map(VesselDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 삭제
     */
    @Transactional
    public void deleteVessel(String vesselId) {
        vesselRepository.deleteById(vesselId);
    }
}
