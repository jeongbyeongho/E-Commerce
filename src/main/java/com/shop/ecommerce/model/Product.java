package com.shop.ecommerce.model;

import com.shop.ecommerce.entity.*;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @Column(nullable = false, length = 200)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stock = 0;

    @Column(length = 500)
    private String imageUrl;

    @Column(length = 100)
    private String brand;

    private Boolean isActive = true;

    private Integer viewCount = 0;

    private Integer salesCount = 0;

    private Double averageRating = 0.0;

    private Integer reviewCount = 0;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    // 기본 생성자
    public Product() {}

    // 전체 생성자
    public Product(Long id, String name, String description, BigDecimal price, Integer stock,
                   String imageUrl, String brand, Boolean isActive, Integer viewCount,
                   Integer salesCount, Double averageRating, Integer reviewCount,
                   LocalDateTime createdAt, LocalDateTime updatedAt, Category category,
                   List<Review> reviews, List<OrderItem> orderItems, List<CartItem> cartItems) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock != null ? stock : 0;
        this.imageUrl = imageUrl;
        this.brand = brand;
        this.isActive = isActive != null ? isActive : true;
        this.viewCount = viewCount != null ? viewCount : 0;
        this.salesCount = salesCount != null ? salesCount : 0;
        this.averageRating = averageRating != null ? averageRating : 0.0;
        this.reviewCount = reviewCount != null ? reviewCount : 0;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.category = category;
        this.reviews = reviews != null ? reviews : new ArrayList<>();
        this.orderItems = orderItems != null ? orderItems : new ArrayList<>();
        this.cartItems = cartItems != null ? cartItems : new ArrayList<>();
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
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public Category getCategory() { return category; }
    public List<Review> getReviews() { return reviews; }
    public List<OrderItem> getOrderItems() { return orderItems; }
    public List<CartItem> getCartItems() { return cartItems; }

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
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setCategory(Category category) { this.category = category; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }
    public void setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; }
    public void setCartItems(List<CartItem> cartItems) { this.cartItems = cartItems; }

    // Builder 패턴
    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    public static class ProductBuilder {
        private Long id;
        private String name;
        private String description;
        private BigDecimal price;
        private Integer stock = 0;
        private String imageUrl;
        private String brand;
        private Boolean isActive = true;
        private Integer viewCount = 0;
        private Integer salesCount = 0;
        private Double averageRating = 0.0;
        private Integer reviewCount = 0;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Category category;
        private List<Review> reviews = new ArrayList<>();
        private List<OrderItem> orderItems = new ArrayList<>();
        private List<CartItem> cartItems = new ArrayList<>();

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
        public ProductBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public ProductBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
        public ProductBuilder category(Category category) { this.category = category; return this; }
        public ProductBuilder reviews(List<Review> reviews) { this.reviews = reviews; return this; }
        public ProductBuilder orderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; return this; }
        public ProductBuilder cartItems(List<CartItem> cartItems) { this.cartItems = cartItems; return this; }

        public Product build() {
            return new Product(id, name, description, price, stock, imageUrl, brand, isActive,
                             viewCount, salesCount, averageRating, reviewCount, createdAt, updatedAt,
                             category, reviews, orderItems, cartItems);
        }
    }

    // 비즈니스 메서드
    public void decreaseStock(int quantity) {
        if (this.stock < quantity) {
            throw new IllegalStateException("재고가 부족합니다.");
        }
        this.stock -= quantity;
    }

    public void increaseStock(int quantity) {
        this.stock += quantity;
    }

    public void updateRating(double newRating, int newReviewCount) {
        this.averageRating = newRating;
        this.reviewCount = newReviewCount;
    }

    public void incrementViewCount() {
        this.viewCount++;
    }

    public void incrementSalesCount(int quantity) {
        this.salesCount += quantity;
    }
}
