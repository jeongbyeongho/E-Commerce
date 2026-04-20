package com.shop.ecommerce.dto;

public class UserLoginRequest {
    private String username;
    private String password;

    // 기본 생성자
    public UserLoginRequest() {}

    // 전체 생성자
    public UserLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter 메서드들
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    // Setter 메서드들
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
}