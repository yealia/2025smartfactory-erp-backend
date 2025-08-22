package com.smartfactory.erp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "materials", uniqueConstraints = {
@UniqueConstraint(name = "uk_material_nm", columnNames = "material_nm")
})
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_id")
    private Integer materialId;   // 자재 ID

    @NotBlank
    @Size(max = 50)
    @Column(name = "material_nm", nullable = false, length = 50)
    private String materialNm;    // 자재명

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String category;      // 카테고리

    @Size(max = 200)
    private String specification; // 상세 규격

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String unit;          // 단위

    @NotNull
    @DecimalMin("0.00")
    @Column(name = "unit_price",nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice; // 기준 단가

    @NotNull
    @DecimalMin("0.00")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal current_price; // 현재 단가

    @NotNull
    @Min(0)
    private Integer min_stock_quantity; // 최소 재고 수량

    @NotNull
    @Min(0)
    private Integer max_stock_quantity; // 최대 재고 수량

    @NotNull
    @Min(0)
    private Integer current_stock;     // 현재 재고

    private Integer lead_time;         // 리드타임(일)

    @NotNull
    private Integer supplier_id;       // 공급업체 ID

    private LocalDate last_purchase_date; // 최종 구매일

    @Builder.Default
    @Column(columnDefinition = "int default 0")
    private Integer status = 0;       // 상태 (0:사용중, 1:단종, 2:대체품 필요)

    @Size(max = 255)
    private String remark;            // 비고

}
