package com.smartfactory.erp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartfactory.erp.entity.CustomerEntity;
import com.smartfactory.erp.entity.SalesOrderEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class SalesOrderDto {
    private String salesOrderId;
    private LocalDate orderDate;
    private String customerId;
    private String vesselId;
    private String customerPoNo;
    private String currencyCode;
    private Integer status;
    private BigDecimal totalAmount;
    private String createdBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime approvedDate;
    private String approvedBy;
    private String remark;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public SalesOrderEntity toEntity() {
        SalesOrderEntity salesOrderEntity = new SalesOrderEntity();
        salesOrderEntity.setSalesOrderId(this.salesOrderId);
        salesOrderEntity.setOrderDate(this.orderDate);
        salesOrderEntity.setCustomerId(this.customerId);
        salesOrderEntity.setVesselId(this.vesselId);
        salesOrderEntity.setCustomerPoNo(this.customerPoNo);
        salesOrderEntity.setCurrencyCode(this.currencyCode);
        salesOrderEntity.setStatus(this.status);
        salesOrderEntity.setTotalAmount(this.totalAmount);
        salesOrderEntity.setCreatedBy(this.createdBy);
        salesOrderEntity.setApprovedDate(this.approvedDate);
        salesOrderEntity.setApprovedBy(this.approvedBy);
        salesOrderEntity.setRemark(this.remark);
        salesOrderEntity.setCreatedAt(this.createdAt);
        salesOrderEntity.setUpdatedAt(this.updatedAt);
        return salesOrderEntity;
    }

    public static SalesOrderDto fromEntity(SalesOrderEntity entity){
        SalesOrderDto dto = new SalesOrderDto();
        dto.setSalesOrderId(entity.getSalesOrderId());
        dto.setOrderDate(entity.getOrderDate());
        dto.setCustomerId(entity.getCustomerId());
        dto.setVesselId(entity.getVesselId());
        dto.setCustomerPoNo(entity.getCustomerPoNo());
        dto.setCurrencyCode(entity.getCurrencyCode());
        dto.setStatus(entity.getStatus());
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setApprovedDate(entity.getApprovedDate());
        dto.setApprovedBy(entity.getApprovedBy());
        dto.setRemark(entity.getRemark());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }


}
