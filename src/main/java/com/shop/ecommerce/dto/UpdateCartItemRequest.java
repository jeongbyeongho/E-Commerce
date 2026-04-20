package com.shop.ecommerce.dto;

public class UpdateCartItemRequest {
    private Integer quantity;

    // 기본 생성자
    public UpdateCartItemRequest() {}

    // 전체 생성자
    public UpdateCartItemRequest(Integer quantity) {
        this.quantity = quantity;
    }

    // Getter 메서드들
    public Integer getQuantity() { return quantity; }

    // Setter 메서드들
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}