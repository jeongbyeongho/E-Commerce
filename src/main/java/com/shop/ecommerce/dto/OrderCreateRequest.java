package com.shop.ecommerce.dto;

import java.math.BigDecimal;
import java.util.List;

public class OrderCreateRequest {
    private List<OrderItemRequest> items;
    private Long addressId;
    private String paymentMethod;
    private String orderMemo;
    private BigDecimal discountAmount;

    // 기본 생성자
    public OrderCreateRequest() {}

    // 전체 생성자
    public OrderCreateRequest(List<OrderItemRequest> items, Long addressId, String paymentMethod,
                              String orderMemo, BigDecimal discountAmount) {
        this.items = items;
        this.addressId = addressId;
        this.paymentMethod = paymentMethod;
        this.orderMemo = orderMemo;
        this.discountAmount = discountAmount;
    }

    // Getter 메서드들
    public List<OrderItemRequest> getItems() { return items; }
    public Long getAddressId() { return addressId; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getOrderMemo() { return orderMemo; }
    public BigDecimal getDiscountAmount() { return discountAmount; }

    // Setter 메서드들
    public void setItems(List<OrderItemRequest> items) { this.items = items; }
    public void setAddressId(Long addressId) { this.addressId = addressId; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public void setOrderMemo(String orderMemo) { this.orderMemo = orderMemo; }
    public void setDiscountAmount(BigDecimal discountAmount) { this.discountAmount = discountAmount; }
}