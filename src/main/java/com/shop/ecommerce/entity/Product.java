package com.shop.ecommerce.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stock;

    private String imageUrl;
    private String brand;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Column(nullable = false)
    private Integer viewCount = 0;

    @Column(nullable = false)
    private Integer salesCount = 0;

    @Column(precision = 3, scale = 2)
    private Double averageRating = 0.0;

    @Column(nullable = false)
    private Integer reviewCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // 기본 생성자
    public Product() {}

    // 전체 생성자
    public Product(Long id, String name, String description, BigDecimal price, Integer stock,
                   String imageUrl, String brand, Boolean isActive, Integer viewCount,
                   Integer salesCount, Double averageRating, Integer reviewCount,
                   Category category, LocalDateTime createdAt, LocalDateTime updatedAt) {
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
        this.category = category;
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
    public Double getAverageRating() { return averageRating; }
    public Integer getReviewCount() { return reviewCount; }
    public Category getCategory() { return category; }
    public List<Review> getReviews() { return reviews; }
    public List<CartItem> getCartItems() { return cartItems; }
    public List<OrderItem> getOrderItems() { return orderItems; }
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
    public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }
    public void setReviewCount(Integer reviewCount) { this.reviewCount = reviewCount; }
    public void setCategory(Category category) { this.category = category; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }
    public void setCartItems(List<CartItem> cartItems) { this.cartItems = cartItems; }
    public void setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // 비즈니스 메서드들
    public void increaseViewCount() {
        this.viewCount++;
    }

    public void increaseSalesCount(int quantity) {
        this.salesCount += quantity;
    }

    public void decreaseStock(int quantity) {
        if (this.stock < quantity) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        this.stock -= quantity;
    }

    public void increaseStock(int quantity) {
        this.stock += quantity;
    }

    public boolean isInStock() {
        return this.stock > 0;
    }

    public boolean isAvailable() {
        return this.isActive && isInStock();
    }

    public void updateRating(double newAverageRating, int newReviewCount) {
        this.averageRating = newAverageRating;
        this.reviewCount = newReviewCount;
    }

    // Builder 패턴
    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    public static class ProductBuilder {
        private Long id;
        private String name;
        private String description;
        private BigDecimal price;
        private Integer stock;
        private String imageUrl;
        private String brand;
        private Boolean isActive = true;
        private Integer viewCount = 0;
        private Integer salesCount = 0;
        private Double averageRating = 0.0;
        private Integer reviewCount = 0;
        private Category category;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public ProductBuilder id(Long id) { this.id = id; return this; }
        public ProductBuilder name(String name) { this.name = name; return this; }
        public ProductBuilder description(String description) { this.description = description; return this; }
        public ProductBuilder price(BigDecimal price) { this.price = price; return this; }
        public ProductBuilder stock(Integer stock) { this.stock = stock; return this; }
        public ProductBuilder imageUrl(String imageUrl) { this.imageUrl = imageUrl; return this; }
        public ProductBuilder brand(String brand) { this.brand = brand; return this; }
        public ProductBuilder isActive(Boolean isActive) { this.isActive = isActive; return this; }
        public ProductBuilder viewCount(Integer viewCount) { this.viewCount = viewCount; return this; }
        public ProductBuilder salesCount(Integer salesCount) { this.salesCount = salesCount; return this; }
        public ProductBuilder averageRating(Double averageRating) { this.averageRating = averageRating; return this; }
        public ProductBuilder reviewCount(Integer reviewCount) { this.reviewCount = reviewCount; return this; }
        public ProductBuilder category(Category category) { this.category = category; return this; }
        public ProductBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public ProductBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Product build() {
            return new Product(id, name, description, price, stock, imageUrl, brand, isActive,
                             viewCount, salesCount, averageRating, reviewCount, category,
                             createdAt, updatedAt);
        }
    }
}