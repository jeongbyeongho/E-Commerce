package com.shop.ecommerce.dto;

public class UserUpdateRequest {
    private String email;
    private String name;
    private String phone;

    // 기본 생성자
    public UserUpdateRequest() {}

    // 전체 생성자
    public UserUpdateRequest(String email, String name, String phone) {
        this.email = email;
        this.name = name;
        this.phone = phone;
    }

    // Getter 메서드들
    public String getEmail() { return email; }
    public String getName() { return name; }
    public String getPhone() { return phone; }

    // Setter 메서드들
    public void setEmail(String email) { this.email = email; }
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
}