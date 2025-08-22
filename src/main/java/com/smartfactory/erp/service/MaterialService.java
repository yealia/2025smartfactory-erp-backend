package com.smartfactory.erp.service;

import java.util.List;
import com.smartfactory.erp.entity.Material;
import com.smartfactory.erp.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaterialService {
    private final MaterialRepository materialRepository;

    // 등록
    public Material saveMaterial(Material material) {
        return materialRepository.save(material);
    }

    // 전체 조회
    public List<Material> getAllMaterials() {
        return materialRepository.findAll();
    }

    // 단건 조회
    public Material getMaterialById(Integer id) {
        return materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("자재를 찾을 수 없습니다. ID=" + id));
    }

    // 삭제
    public void deleteMaterial(Integer id) {
        materialRepository.deleteById(id);
    }
}
