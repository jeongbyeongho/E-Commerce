package com.shop.ecommerce.dto;

import java.math.BigDecimal;

public class ProductUpdateRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String imageUrl;
    private String brand;
    private Long categoryId;
    private Boolean isActive;

    // 기본 생성자
    public ProductUpdateRequest() {}

    // 전체 생성자
    public ProductUpdateRequest(String name, String description, BigDecimal price, Integer stock,
                                String imageUrl, String brand, Long categoryId, Boolean isActive) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.brand = brand;
        this.categoryId = categoryId;
        this.isActive = isActive;
    }

    // Getter 메서드들
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public Integer getStock() { return stock; }
    public String getImageUrl() { return imageUrl; }
    public String getBrand() { return brand; }
    public Long getCategoryId() { return categoryId; }
    public Boolean getIsActive() { return isActive; }

    // Setter 메서드들
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setStock(Integer stock) { this.stock = stock; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}