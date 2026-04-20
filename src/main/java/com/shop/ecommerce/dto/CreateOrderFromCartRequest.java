package com.shop.ecommerce.dto;

public class CreateOrderFromCartRequest {
    private Long addressId;
    private String paymentMethod;
    private String orderMemo;

    // 기본 생성자
    public CreateOrderFromCartRequest() {}

    // 전체 생성자
    public CreateOrderFromCartRequest(Long addressId, String paymentMethod, String orderMemo) {
        this.addressId = addressId;
        this.paymentMethod = paymentMethod;
        this.orderMemo = orderMemo;
    }

    // Getter 메서드들
    public Long getAddressId() { return addressId; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getOrderMemo() { return orderMemo; }

    // Setter 메서드들
    public void setAddressId(Long addressId) { this.addressId = addressId; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public void setOrderMemo(String orderMemo) { this.orderMemo = orderMemo; }
}