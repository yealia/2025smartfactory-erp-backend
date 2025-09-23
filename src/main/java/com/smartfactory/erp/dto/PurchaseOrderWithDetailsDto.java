package com.smartfactory.erp.dto;

import com.smartfactory.erp.entity.PurchaseDetailEntity;
import com.smartfactory.erp.entity.PurchaseOrderEntity;
import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class PurchaseOrderWithDetailsDto {
    private PurchaseOrderDto purchaseOrder;
    private List<PurchaseDetailDto> orderDetails;

    public static PurchaseOrderWithDetailsDto fromEntity(PurchaseOrderEntity entity) {
        PurchaseOrderWithDetailsDto dto = new PurchaseOrderWithDetailsDto();

        // 1. 상위 발주 정보 변환
        dto.setPurchaseOrder(PurchaseOrderDto.fromEntity(entity));

        // 2. 하위 상세 정보 리스트 변환 (null-safe 처리 포함)
        List<PurchaseDetailEntity> details = Optional.ofNullable(entity.getPurchaseOrderDetails())
                .orElse(Collections.emptyList());

        dto.setOrderDetails(
                details.stream()
                        .map(PurchaseDetailDto::fromEntity)
                        .collect(Collectors.toList())
        );

        return dto;
    }
}
