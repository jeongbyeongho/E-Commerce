package com.shop.ecommerce.dto;

public class RefreshTokenRequest {
    private String refreshToken;

    // 기본 생성자
    public RefreshTokenRequest() {}

    // 전체 생성자
    public RefreshTokenRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    // Getter 메서드들
    public String getRefreshToken() { return refreshToken; }

    // Setter 메서드들
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
}