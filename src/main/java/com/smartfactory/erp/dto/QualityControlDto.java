package com.smartfactory.erp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.time.LocalDateTime;

// MES에서 넘어오는 JSON에 없는 필드는 무시하도록 설정
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class QualityControlDto {
    private Integer qcId;
    private String purchaseOrderId;
    private Integer orderDetailId;
    private Integer workOrderId;
    private Integer materialId;
    private String inspectorId;
    private LocalDateTime inspectionDate;
    private String result;
    private int passQuantity;
    private int failQuantity;
    private String defectType;
    private String remark;
}
