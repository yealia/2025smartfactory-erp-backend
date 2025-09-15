package com.smartfactory.erp.dto;

import lombok.Data;

import java.util.List;

@Data
public class PurchaseOrderWithDetailsDto {
    private PurchaseOrderDto purchaseOrder;
    private List<PurchaseDetailDto> orderDetails;
}
