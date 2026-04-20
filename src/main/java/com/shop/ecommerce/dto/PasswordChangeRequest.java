package com.shop.ecommerce.dto;

public class PasswordChangeRequest {
    private String currentPassword;
    private String newPassword;

    // 기본 생성자
    public PasswordChangeRequest() {}

    // 전체 생성자
    public PasswordChangeRequest(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    // Getter 메서드들
    public String getCurrentPassword() { return currentPassword; }
    public String getNewPassword() { return newPassword; }

    // Setter 메서드들
    public void setCurrentPassword(String currentPassword) { this.currentPassword = currentPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
}