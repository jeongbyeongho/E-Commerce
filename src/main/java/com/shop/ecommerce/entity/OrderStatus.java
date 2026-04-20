package com.shop.ecommerce.entity;

/**
 * 주문 상태 Enum
 */
public enum OrderStatus {
    PENDING,        // 주문 대기
    PAID,           // 결제 완료
    PREPARING,      // 상품 준비중
    SHIPPED,        // 배송중
    DELIVERED,      // 배송 완료
    CANCELED,       // 주문 취소
    REFUNDED        // 환불 완료
}