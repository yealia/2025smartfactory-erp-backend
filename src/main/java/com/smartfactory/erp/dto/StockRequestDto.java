package com.smartfactory.erp.dto;

import lombok.Data;

/**
 * 재고 증감/조정 요청을 위한 DTO
 * ---------------------------------------------
 * 이 객체는 외부 시스템(MES 등)에서 ERP로 API 호출 시,
 * 어떤 재고를 얼마만큼 변경할지 전달하는 요청 바디로 사용됩니다.
 *
 * 특징:
 * - InventoryDto(조회/DB 매핑용)와 분리하여, 요청에 필요한 최소 데이터만 가짐
 * - Controller의 @RequestBody 파라미터로 매핑됨
 * - quantity는 음수/양수 모두 가능
 *   (예: 차감 시 -5, 복구 시 +5, 절대값 세팅 시는 setOnHand 사용)
 */
@Data
public class StockRequestDto {

    /** 재고 ID (선택 사항) - 특정 재고 레코드를 고유하게 지정할 때 사용 */
    private String inventoryId;

    /** 자재 ID - 필수. 어떤 자재(material)에 대한 변경인지 지정 */
    private Integer materialId;

    /** 창고 - 필수. 어떤 창고에서 변경되는지 지정 */
    private String warehouse;

    /** 위치 - 필수. 창고 내 위치/로케이션 지정 */
    private String location;

    /** 변경 수량
     *  - 양수: 입고 / 복구
     *  - 음수: 출고 / 차감
     *  - 절대 세팅은 InventoryService.setOnHand()로 별도 처리
     */
    private int quantity;
}