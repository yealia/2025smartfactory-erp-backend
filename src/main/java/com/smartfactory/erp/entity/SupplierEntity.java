package com.smartfactory.erp.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "suppliers")
public class SupplierEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id", nullable = false)
    private Integer supplierId;

    @Column(name = "supplier_name", nullable = false, length = 50)
    private String supplierName;

    @Column(name = "contact_name", length = 20)
    private String contactName;

    @Column(name = "contact_phone", length = 20)
    private String contactPhone;

    @Column(name = "contact_address", length = 100)
    private String contactAddress;

    @Column(name = "contact_email", length = 50)
    private String contactEmail;

    @Column(name = "contract_date")
    private LocalDate contractDate;

    @Column(name = "created_at", updatable = false, insertable = false,
            columnDefinition = "datetime default current_timestamp")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false,
            columnDefinition = "datetime on update current_timestamp")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "supplier")
    private List<MaterialEntity> materials;

    @OneToMany(mappedBy = "supplier")
    private List<PurchaseOrderEntity> purchaseOrders;
}