package com.shop.ecommerce.dto;

public class OrderStatusUpdateRequest {
    private String status;
    private String trackingNumber;

    // 기본 생성자
    public OrderStatusUpdateRequest() {}

    // 전체 생성자
    public OrderStatusUpdateRequest(String status, String trackingNumber) {
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