package com.shop.ecommerce.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "addresses")
public class Address {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 50)
    private String recipientName;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(nullable = false, length = 10)
    private String zipCode;

    @Column(nullable = false, length = 200)
    private String address;

    @Column(length = 200)
    private String detailAddress;

    @Column(length = 50)
    private String addressType; // 집, 회사 등

    private Boolean isDefault = false;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // 기본 생성자
    public Address() {}

    // 전체 생성자
    public Address(Long id, User user, String recipientName, String phone, String zipCode,
                   String address, String detailAddress, String addressType, Boolean isDefault,
                   LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.recipientName = recipientName;
        this.phone = phone;
        this.zipCode = zipCode;
        this.address = address;
        this.detailAddress = detailAddress;
        this.addressType = addressType;
        this.isDefault = isDefault != null ? isDefault : false;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter 메서드들
    public Long getId() { return id; }
    public User getUser() { return user; }
    public String getRecipientName() { return recipientName; }
    public String getPhone() { return phone; }
    public String getZipCode() { return zipCode; }
    public String getAddress() { return address; }
    public String getDetailAddress() { return detailAddress; }
    public String getAddressType() { return addressType; }
    public Boolean getIsDefault() { return isDefault; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Setter 메서드들
    public void setId(Long id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
    public void setAddress(String address) { this.address = address; }
    public void setDetailAddress(String detailAddress) { this.detailAddress = detailAddress; }
    public void setAddressType(String addressType) { this.addressType = addressType; }
    public void setIsDefault(Boolean isDefault) { this.isDefault = isDefault; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // Builder 패턴
    public static AddressBuilder builder() {
        return new AddressBuilder();
    }

    public static class AddressBuilder {
        private Long id;
        private User user;
        private String recipientName;
        private String phone;
        private String zipCode;
        private String address;
        private String detailAddress;
        private String addressType;
        private Boolean isDefault = false;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public AddressBuilder id(Long id) { this.id = id; return this; }
        public AddressBuilder user(User user) { this.user = user; return this; }
        public AddressBuilder recipientName(String recipientName) { this.recipientName = recipientName; return this; }
        public AddressBuilder phone(String phone) { this.phone = phone; return this; }
        public AddressBuilder zipCode(String zipCode) { this.zipCode = zipCode; return this; }
        public AddressBuilder address(String address) { this.address = address; return this; }
        public AddressBuilder detailAddress(String detailAddress) { this.detailAddress = detailAddress; return this; }
        public AddressBuilder addressType(String addressType) { this.addressType = addressType; return this; }
        public AddressBuilder isDefault(Boolean isDefault) { this.isDefault = isDefault; return this; }
        public AddressBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public AddressBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Address build() {
            return new Address(id, user, recipientName, phone, zipCode, address, detailAddress,
                             addressType, isDefault, createdAt, updatedAt);
        }
    }

    // 비즈니스 메서드
    public String getFullAddress() {
        return String.format("[%s] %s %s", zipCode, address, detailAddress != null ? detailAddress : "");
    }
}
