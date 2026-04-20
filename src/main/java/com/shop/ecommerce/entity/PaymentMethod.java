package com.shop.ecommerce.entity;

/**
 * 결제 방법 Enum
 */
public enum PaymentMethod {
    CARD,           // 신용카드
    BANK_TRANSFER,  // 계좌이체
    VIRTUAL_ACCOUNT,// 가상계좌
    PHONE,          // 휴대폰 결제
    KAKAO_PAY,      // 카카오페이
    NAVER_PAY,      // 네이버페이
    TOSS           // 토스페이
}