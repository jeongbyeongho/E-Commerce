package com.shop.ecommerce.dto;

public class UserRegisterRequest {
    private String username;
    private String email;
    private String password;
    private String name;
    private String phone;

    // 기본 생성자
    public UserRegisterRequest() {}

    // 전체 생성자
    public UserRegisterRequest(String username, String email, String password, String name, String phone) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
    }

    // Getter 메서드들
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public String getPhone() { return phone; }

    // Setter 메서드들
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
}