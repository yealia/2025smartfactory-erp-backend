package com.smartfactory.erp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
public class InspectionRequestDto {
    private String purchaseOrderId;   // 발주 ID
    //private String orderDetailId;     // 발주 상세
    //private Integer materialId;       // 자재
    private String inspectorId;   // 프런트에서 던짐
    private Integer orderDetailId;
    private Integer materialId;
    private Integer orderQuantity; //20250904추가
    //private List<Detail> details;     // 발주 상세(내부 클래스 사용)

//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class Detail {
//        private Integer orderDetailId;
//        private Integer materialId;
//        //private Integer orderQuantity;
//        //private Integer receivedQuantity;
//    }
}
