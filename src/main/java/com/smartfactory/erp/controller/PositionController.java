package com.smartfactory.erp.controller;

import com.smartfactory.erp.dto.PositionDto;
import com.smartfactory.erp.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/positions") // 1. React에서 요청한 주소와 일치시킵니다.
@RequiredArgsConstructor
// 2. React(5173포트)와 Spring Boot(8081포트) 포트가 다르므로 CORS 문제를 방지하기 위해 추가합니다.
@CrossOrigin(origins = "http://localhost:5173")
public class PositionController {

    private final PositionService positionService;

    /**
     * 모든 직책 정보를 조회하는 API 엔드포인트입니다.
     * @return 직책 DTO 리스트와 HTTP 200 OK 상태 코드를 반환합니다.
     */
    @GetMapping // 3. GET /api/positions 요청을 이 메서드와 매핑합니다.
    public ResponseEntity<List<PositionDto>> getAllPositions() {
        List<PositionDto> positions = positionService.findAll();
        return ResponseEntity.ok(positions);
    }
}
