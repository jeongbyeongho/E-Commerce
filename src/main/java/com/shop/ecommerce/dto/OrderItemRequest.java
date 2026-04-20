package com.shop.ecommerce.dto;

public class OrderItemRequest {
    private Long productId;
    private Integer quantity;

    // 기본 생성자
    public OrderItemRequest() {}

    // 전체 생성자
    public OrderItemRequest(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getter 메서드들
    public Long getProductId() { return productId; }
    public Integer getQuantity() { return quantity; }

    // Setter 메서드들
    public void setProductId(Long productId) { this.productId = productId; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}