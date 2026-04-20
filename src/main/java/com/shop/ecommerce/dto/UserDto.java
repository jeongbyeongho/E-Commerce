package com.shop.ecommerce.dto;

import java.time.LocalDateTime;

public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String name;
    private String phone;
    private String role;
    private Boolean enabled;
    private LocalDateTime createdAt;

    // 기본 생성자
    public UserDto() {}

    // 전체 생성자
    public UserDto(Long id, String username, String email, String name, String phone,
                   String role, Boolean enabled, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.role = role;
        this.enabled = enabled;
        this.createdAt = createdAt;
    }

    // Getter 메서드들
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getRole() { return role; }
    public Boolean getEnabled() { return enabled; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // Setter 메서드들
    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setRole(String role) { this.role = role; }
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // Builder 패턴
    public static UserDtoBuilder builder() {
        return new UserDtoBuilder();
    }

    public static class UserDtoBuilder {
        private Long id;
        private String username;
        private String email;
        private String name;
        private String phone;
        private String role;
        private Boolean enabled;
        private LocalDateTime createdAt;

        public UserDtoBuilder id(Long id) { this.id = id; return this; }
        public UserDtoBuilder username(String username) { this.username = username; return this; }
        public UserDtoBuilder email(String email) { this.email = email; return this; }
        public UserDtoBuilder name(String name) { this.name = name; return this; }
        public UserDtoBuilder phone(String phone) { this.phone = phone; return this; }
        public UserDtoBuilder role(String role) { this.role = role; return this; }
        public UserDtoBuilder enabled(Boolean enabled) { this.enabled = enabled; return this; }
        public UserDtoBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public UserDto build() {
            return new UserDto(id, username, email, name, phone, role, enabled, createdAt);
        }
    }
}
