package com.shop.ecommerce.dto;

public class CreateAddressRequest {
    private String recipientName;
    private String phone;
    private String zipCode;
    private String address;
    private String detailAddress;
    private String addressType;
    private Boolean isDefault;

    // 기본 생성자
    public CreateAddressRequest() {}

    // 전체 생성자
    public CreateAddressRequest(String recipientName, String phone, String zipCode, String address,
                                String detailAddress, String addressType, Boolean isDefault) {
        this.recipientName = recipientName;
        this.phone = phone;
        this.zipCode = zipCode;
        this.address = address;
        this.detailAddress = detailAddress;
        this.addressType = addressType;
        this.isDefault = isDefault;
    }

    // Getter 메서드들
    public String getRecipientName() { return recipientName; }
    public String getPhone() { return phone; }
    public String getZipCode() { return zipCode; }
    public String getAddress() { return address; }
    public String getDetailAddress() { return detailAddress; }
    public String getAddressType() { return addressType; }
    public Boolean getIsDefault() { return isDefault; }

    // Setter 메서드들
    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
    public void setAddress(String address) { this.address = address; }
    public void setDetailAddress(String detailAddress) { this.detailAddress = detailAddress; }
    public void setAddressType(String addressType) { this.addressType = addressType; }
    public void setIsDefault(Boolean isDefault) { this.isDefault = isDefault; }
}