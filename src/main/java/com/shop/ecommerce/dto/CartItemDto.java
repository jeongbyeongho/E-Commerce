package com.shop.ecommerce.dto;

import java.math.BigDecimal;

public class CartItemDto {
    private Long id;
    private Long productId;
    private String productName;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal totalPrice;
    private String imageUrl;
    private Integer stock;

    // 기본 생성자
    public CartItemDto() {}

    // 전체 생성자
    public CartItemDto(Long id, Long productId, String productName, BigDecimal price, 
                       Integer quantity, BigDecimal totalPrice, String imageUrl, Integer stock) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.imageUrl = imageUrl;
        this.stock = stock;
    }

    // Getter 메서드들
    public Long getId() { return id; }
    public Long getProductId() { return productId; }
    public String getProductName() { return productName; }
    public BigDecimal getPrice() { return price; }
    public Integer getQuantity() { return quantity; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public String getImageUrl() { return imageUrl; }
    public Integer getStock() { return stock; }

    // Setter 메서드들
    public void setId(Long id) { this.id = id; }
    public void setProductId(Long productId) { this.productId = productId; }
    public void setProductName(String productName) { this.productName = productName; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setStock(Integer stock) { this.stock = stock; }

    // Builder 패턴
    public static CartItemDtoBuilder builder() {
        return new CartItemDtoBuilder();
    }

    public static class CartItemDtoBuilder {
        private Long id;
        private Long productId;
        private String productName;
        private BigDecimal price;
        private Integer quantity;
        private BigDecimal totalPrice;
        private String imageUrl;
        private Integer stock;

        public CartItemDtoBuilder id(Long id) { this.id = id; return this; }
        public CartItemDtoBuilder productId(Long productId) { this.productId = productId; return this; }
        public CartItemDtoBuilder productName(String productName) { this.productName = productName; return this; }
        public CartItemDtoBuilder price(BigDecimal price) { this.price = price; return this; }
        public CartItemDtoBuilder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public CartItemDtoBuilder totalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; return this; }
        public CartItemDtoBuilder imageUrl(String imageUrl) { this.imageUrl = imageUrl; return this; }
        public CartItemDtoBuilder stock(Integer stock) { this.stock = stock; return this; }

        public CartItemDto build() {
            return new CartItemDto(id, productId, productName, price, quantity, totalPrice, imageUrl, stock);
        }
    }
}