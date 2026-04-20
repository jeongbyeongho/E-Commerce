package com.shop.ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String imageUrl;
    private String brand;
    private Boolean isActive;
    private Integer viewCount;
    private Integer salesCount;
    private BigDecimal averageRating;
    private Integer reviewCount;
    private Long categoryId;
    private String categoryName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 기본 생성자
    public ProductDto() {}

    // 전체 생성자
    public ProductDto(Long id, String name, String description, BigDecimal price, Integer stock,
                      String imageUrl, String brand, Boolean isActive, Integer viewCount,
                      Integer salesCount, BigDecimal averageRating, Integer reviewCount,
                      Long categoryId, String categoryName, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.brand = brand;
        this.isActive = isActive;
        this.viewCount = viewCount;
        this.salesCount = salesCount;
        this.averageRating = averageRating;
        this.reviewCount = reviewCount;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter 메서드들
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public Integer getStock() { return stock; }
    public String getImageUrl() { return imageUrl; }
    public String getBrand() { return brand; }
    public Boolean getIsActive() { return isActive; }
    public Integer getViewCount() { return viewCount; }
    public Integer getSalesCount() { return salesCount; }
    public BigDecimal getAverageRating() { return averageRating; }
    public Integer getReviewCount() { return reviewCount; }
    public Long getCategoryId() { return categoryId; }
    public String getCategoryName() { return categoryName; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Setter 메서드들
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setStock(Integer stock) { this.stock = stock; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    public void setViewCount(Integer viewCount) { this.viewCount = viewCount; }
    public void setSalesCount(Integer salesCount) { this.salesCount = salesCount; }
    public void setAverageRating(BigDecimal averageRating) { this.averageRating = averageRating; }
    public void setReviewCount(Integer reviewCount) { this.reviewCount = reviewCount; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // Builder 패턴
    public static ProductDtoBuilder builder() {
        return new ProductDtoBuilder();
    }

    public static class ProductDtoBuilder {
        private Long id;
        private String name;
        private String description;
        private BigDecimal price;
        private Integer stock;
        private String imageUrl;
        private String brand;
        private Boolean isActive;
        private Integer viewCount;
        private Integer salesCount;
        private BigDecimal averageRating;
        private Integer reviewCount;
        private Long categoryId;
        private String categoryName;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public ProductDtoBuilder id(Long id) { this.id = id; return this; }
        public ProductDtoBuilder name(String name) { this.name = name; return this; }
        public ProductDtoBuilder description(String description) { this.description = description; return this; }
        public ProductDtoBuilder price(BigDecimal price) { this.price = price; return this; }
        public ProductDtoBuilder stock(Integer stock) { this.stock = stock; return this; }
        public ProductDtoBuilder imageUrl(String imageUrl) { this.imageUrl = imageUrl; return this; }
        public ProductDtoBuilder brand(String brand) { this.brand = brand; return this; }
        public ProductDtoBuilder isActive(Boolean isActive) { this.isActive = isActive; return this; }
        public ProductDtoBuilder viewCount(Integer viewCount) { this.viewCount = viewCount; return this; }
        public ProductDtoBuilder salesCount(Integer salesCount) { this.salesCount = salesCount; return this; }
        public ProductDtoBuilder averageRating(BigDecimal averageRating) { this.averageRating = averageRating; return this; }
        public ProductDtoBuilder reviewCount(Integer reviewCount) { this.reviewCount = reviewCount; return this; }
        public ProductDtoBuilder categoryId(Long categoryId) { this.categoryId = categoryId; return this; }
        public ProductDtoBuilder categoryName(String categoryName) { this.categoryName = categoryName; return this; }
        public ProductDtoBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public ProductDtoBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public ProductDto build() {
            return new ProductDto(id, name, description, price, stock, imageUrl, brand, isActive,
                                viewCount, salesCount, averageRating, reviewCount, categoryId,
                                categoryName, createdAt, updatedAt);
        }
    }
}
