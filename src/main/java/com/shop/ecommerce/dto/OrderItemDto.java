package com.shop.ecommerce.dto;

import java.math.BigDecimal;

public class OrderItemDto {
    private Long id;
    private Long productId;
    private String productName;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal totalPrice;

    // 기본 생성자
    public OrderItemDto() {}

    // 전체 생성자
    public OrderItemDto(Long id, Long productId, String productName, BigDecimal price,
                        Integer quantity, BigDecimal totalPrice) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    // Getter 메서드들
    public Long getId() { return id; }
    public Long getProductId() { return productId; }
    public String getProductName() { return productName; }
    public BigDecimal getPrice() { return price; }
    public Integer getQuantity() { return quantity; }
    public BigDecimal getTotalPrice() { return totalPrice; }

    // Setter 메서드들
    public void setId(Long id) { this.id = id; }
    public void setProductId(Long productId) { this.productId = productId; }
    public void setProductName(String productName) { this.productName = productName; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    // Builder 패턴
    public static OrderItemDtoBuilder builder() {
        return new OrderItemDtoBuilder();
    }

    public static class OrderItemDtoBuilder {
        private Long id;
        private Long productId;
        private String productName;
        private BigDecimal price;
        private Integer quantity;
        private BigDecimal totalPrice;

        public OrderItemDtoBuilder id(Long id) { this.id = id; return this; }
        public OrderItemDtoBuilder productId(Long productId) { this.productId = productId; return this; }
        public OrderItemDtoBuilder productName(String productName) { this.productName = productName; return this; }
        public OrderItemDtoBuilder price(BigDecimal price) { this.price = price; return this; }
        public OrderItemDtoBuilder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public OrderItemDtoBuilder totalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; return this; }

        public OrderItemDto build() {
            return new OrderItemDto(id, productId, productName, price, quantity, totalPrice);
        }
    }
}