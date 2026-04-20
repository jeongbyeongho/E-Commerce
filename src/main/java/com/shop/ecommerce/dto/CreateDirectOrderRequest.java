package com.shop.ecommerce.dto;

import java.util.List;

public class CreateDirectOrderRequest {
    private List<OrderItemRequest> items;
    private Long addressId;
    private String paymentMethod;
    private String orderMemo;

    // 기본 생성자
    public CreateDirectOrderRequest() {}

    // 전체 생성자
    public CreateDirectOrderRequest(List<OrderItemRequest> items, Long addressId, String paymentMethod, String orderMemo) {
        this.items = items;
        this.addressId = addressId;
        this.paymentMethod = paymentMethod;
        this.orderMemo = orderMemo;
    }

    // Getter 메서드들
    public List<OrderItemRequest> getItems() { return items; }
    public Long getAddressId() { return addressId; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getOrderMemo() { return orderMemo; }

    // Setter 메서드들
    public void setItems(List<OrderItemRequest> items) { this.items = items; }
    public void setAddressId(Long addressId) { this.addressId = addressId; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public void setOrderMemo(String orderMemo) { this.orderMemo = orderMemo; }
}