package com.smartfactory.erp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartfactory.erp.entity.CustomerEntity;
import com.smartfactory.erp.entity.SalesOrderEntity;
import com.smartfactory.erp.entity.VesselEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class SalesOrderDto {
    private String salesOrderId;
    private LocalDate orderDate;

    // ID 필드
    private String customerId;
    private String vesselId;

    // ✅ [추가] 화면 표시에 유용한 이름 필드
    private String customerName;
    private String vesselName;

    private String customerPoNo;
    private String currencyCode;
    private Integer status;
    private BigDecimal totalAmount;
    private String createdBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime approvedDate;
    private String approvedBy;
    private String remark;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    /**
     * DTO를 엔티티로 변환하는 메소드 (주로 생성/수정 시 사용)
     * @return SalesOrderEntity 객체
     */
    public SalesOrderEntity toEntity() {
        SalesOrderEntity salesOrderEntity = new SalesOrderEntity();
        salesOrderEntity.setSalesOrderId(this.salesOrderId);
        salesOrderEntity.setOrderDate(this.orderDate);
        salesOrderEntity.setCustomerPoNo(this.customerPoNo);
        salesOrderEntity.setCurrencyCode(this.currencyCode);
        salesOrderEntity.setStatus(this.status);
        salesOrderEntity.setTotalAmount(this.totalAmount);
        salesOrderEntity.setCreatedBy(this.createdBy);
        salesOrderEntity.setApprovedDate(this.approvedDate);
        salesOrderEntity.setApprovedBy(this.approvedBy);
        salesOrderEntity.setRemark(this.remark);

        // ✅ [수정] ID를 사용하여 연관 엔티티의 참조를 설정합니다.
        // JPA는 이 ID를 사용하여 DB에 저장할 때 올바른 외래 키를 설정합니다.
        if (this.customerId != null) {
            CustomerEntity customer = new CustomerEntity();
            customer.setCustomerId(this.customerId);
            salesOrderEntity.setCustomer(customer);
        }

        if (this.vesselId != null) {
            VesselEntity vessel = new VesselEntity();
            vessel.setVesselId(this.vesselId);
            salesOrderEntity.setVessel(vessel);
        }

        return salesOrderEntity;
    }

    /**
     * 엔티티를 DTO로 변환하는 정적 메소드 (주로 조회 시 사용)
     * @param entity SalesOrderEntity 객체
     * @return SalesOrderDto 객체
     */
    public static SalesOrderDto fromEntity(SalesOrderEntity entity) {
        SalesOrderDto dto = new SalesOrderDto();
        dto.setSalesOrderId(entity.getSalesOrderId());
        dto.setOrderDate(entity.getOrderDate());
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

        if (entity.getCustomer() != null) {
            dto.setCustomerId(entity.getCustomer().getCustomerId());
            dto.setCustomerName(entity.getCustomer().getCustomerNm());
        }

        if (entity.getVessel() != null) {
            dto.setVesselId(entity.getVessel().getVesselId());
            dto.setVesselName(entity.getVessel().getVesselNm());
        }

        return dto;
    }
}
