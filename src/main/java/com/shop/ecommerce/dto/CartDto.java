package com.shop.ecommerce.dto;

import java.math.BigDecimal;
import java.util.List;

public class CartDto {
    private Long id;
    private Long userId;
    private List<CartItemDto> items;
    private BigDecimal totalPrice;
    private Integer totalQuantity;

    // 기본 생성자
    public CartDto() {}

    // 전체 생성자
    public CartDto(Long id, Long userId, List<CartItemDto> items, BigDecimal totalPrice, Integer totalQuantity) {
        this.id = id;
        this.userId = userId;
        this.items = items;
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
    }

    // Getter 메서드들
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public List<CartItemDto> getItems() { return items; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public Integer getTotalQuantity() { return totalQuantity; }

    // Setter 메서드들
    public void setId(Long id) { this.id = id; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setItems(List<CartItemDto> items) { this.items = items; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
    public void setTotalQuantity(Integer totalQuantity) { this.totalQuantity = totalQuantity; }

    // Builder 패턴
    public static CartDtoBuilder builder() {
        return new CartDtoBuilder();
    }

    public static class CartDtoBuilder {
        private Long id;
        private Long userId;
        private List<CartItemDto> items;
        private BigDecimal totalPrice;
        private Integer totalQuantity;

        public CartDtoBuilder id(Long id) { this.id = id; return this; }
        public CartDtoBuilder userId(Long userId) { this.userId = userId; return this; }
        public CartDtoBuilder items(List<CartItemDto> items) { this.items = items; return this; }
        public CartDtoBuilder totalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; return this; }
        public CartDtoBuilder totalQuantity(Integer totalQuantity) { this.totalQuantity = totalQuantity; return this; }

        public CartDto build() {
            return new CartDto(id, userId, items, totalPrice, totalQuantity);
        }
    }
}
