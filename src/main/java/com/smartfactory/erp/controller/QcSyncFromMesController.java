package com.smartfactory.erp.controller;

import com.smartfactory.erp.service.QcSyncFromMesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sync")
@RequiredArgsConstructor
public class QcSyncFromMesController { // ★ 클래스 이름 변경

    private final QcSyncFromMesService qcSyncFromMesService;

    @PostMapping("/from-mes")
    public ResponseEntity<String> syncFromMes() {
        qcSyncFromMesService.syncFromMes();
        return ResponseEntity.ok("MES 데이터 동기화가 완료되었습니다.");
    }
}
