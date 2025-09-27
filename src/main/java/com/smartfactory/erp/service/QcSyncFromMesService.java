package com.smartfactory.erp.service;

import com.smartfactory.erp.dto.QualityControlDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QcSyncFromMesService {

    private final InventoryService inventoryService; // 기존 재고 처리 서비스
    private final RestTemplate restTemplate;

    public void syncFromMes() {
        // 1. MES 서버의 API를 호출해서 완료된 QC 목록을 가져옴
        String mesApiUrl = "http://localhost:8082/api/qualityControl/completed-for-erp";

        List<QualityControlDto> qcResults = restTemplate.exchange(
                mesApiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<QualityControlDto>>() {}
        ).getBody();

        // 2. 가져온 데이터 각각에 대해 재고 및 이동 기록 업데이트
        if (qcResults != null) {
            for (QualityControlDto qc : qcResults) {
                inventoryService.processQcResult(qc);
            }
        }
    }
}
