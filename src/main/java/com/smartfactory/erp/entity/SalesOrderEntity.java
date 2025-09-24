package com.smartfactory.erp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sales_orders")
public class SalesOrderEntity {

    @Id
    @Column(name = "sales_order_id", length = 20)
    private String salesOrderId;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @Column(name = "customer_po_no", length = 30)
    private String customerPoNo;

    @Column(name = "currency_code", length = 3)
    private String currencyCode;

    @Column(name = "status")
    private Integer status;

    @Column(name = "total_amount", precision = 15, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "created_by", length = 20, nullable = false)
    private String createdBy;

    @Column(name = "approved_date")
    private LocalDateTime approvedDate;

    @Column(name = "approved_by", length = 20)
    private String approvedBy;

    @Column(name = "remark", length = 255)
    private String remark;

    @Column(name = "created_at", updatable = false, insertable = false,
            columnDefinition = "datetime default current_timestamp")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false,
            columnDefinition = "datetime on update current_timestamp")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vessel_id", nullable = false)
    private VesselEntity vessel;
}