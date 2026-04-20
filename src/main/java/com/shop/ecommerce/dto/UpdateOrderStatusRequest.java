package com.shop.ecommerce.dto;

public class UpdateOrderStatusRequest {
    private String status;
    private String trackingNumber;

    // 기본 생성자
    public UpdateOrderStatusRequest() {}

    // 전체 생성자
    public UpdateOrderStatusRequest(String status, String trackingNumber) {
        this.status = status;
        this.trackingNumber = trackingNumber;
    }

    // Getter 메서드들
    public String getStatus() { return status; }
    public String getTrackingNumber() { return trackingNumber; }

    // Setter 메서드들
    public void setStatus(String status) { this.status = status; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }
}