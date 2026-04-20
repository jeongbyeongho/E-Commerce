package com.shop.ecommerce.entity;

import com.shop.ecommerce.entity.Product;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer rating; // 1-5점

    @Column(length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(length = 500)
    private String imageUrl;

    private Integer helpfulCount = 0;

    private Boolean isVerifiedPurchase = false;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // 기본 생성자
    public Review() {}

    // 전체 생성자
    public Review(Long id, Product product, User user, Integer rating, String title, String content,
                  String imageUrl, Integer helpfulCount, Boolean isVerifiedPurchase,
                  LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.product = product;
        this.user = user;
        this.rating = rating;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.helpfulCount = helpfulCount != null ? helpfulCount : 0;
        this.isVerifiedPurchase = isVerifiedPurchase != null ? isVerifiedPurchase : false;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter 메서드들
    public Long getId() { return id; }
    public Product getProduct() { return product; }
    public User getUser() { return user; }
    public Integer getRating() { return rating; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getImageUrl() { return imageUrl; }
    public Integer getHelpfulCount() { return helpfulCount; }
    public Boolean getIsVerifiedPurchase() { return isVerifiedPurchase; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Setter 메서드들
    public void setId(Long id) { this.id = id; }
    public void setProduct(Product product) { this.product = product; }
    public void setUser(User user) { this.user = user; }
    public void setRating(Integer rating) { this.rating = rating; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setHelpfulCount(Integer helpfulCount) { this.helpfulCount = helpfulCount; }
    public void setIsVerifiedPurchase(Boolean isVerifiedPurchase) { this.isVerifiedPurchase = isVerifiedPurchase; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // Builder 패턴
    public static ReviewBuilder builder() {
        return new ReviewBuilder();
    }

    public static class ReviewBuilder {
        private Long id;
        private Product product;
        private User user;
        private Integer rating;
        private String title;
        private String content;
        private String imageUrl;
        private Integer helpfulCount = 0;
        private Boolean isVerifiedPurchase = false;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public ReviewBuilder id(Long id) { this.id = id; return this; }
        public ReviewBuilder product(Product product) { this.product = product; return this; }
        public ReviewBuilder user(User user) { this.user = user; return this; }
        public ReviewBuilder rating(Integer rating) { this.rating = rating; return this; }
        public ReviewBuilder title(String title) { this.title = title; return this; }
        public ReviewBuilder content(String content) { this.content = content; return this; }
        public ReviewBuilder imageUrl(String imageUrl) { this.imageUrl = imageUrl; return this; }
        public ReviewBuilder helpfulCount(Integer helpfulCount) { this.helpfulCount = helpfulCount; return this; }
        public ReviewBuilder isVerifiedPurchase(Boolean isVerifiedPurchase) { this.isVerifiedPurchase = isVerifiedPurchase; return this; }
        public ReviewBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public ReviewBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Review build() {
            return new Review(id, product, user, rating, title, content, imageUrl, helpfulCount,
                            isVerifiedPurchase, createdAt, updatedAt);
        }
    }

    // 비즈니스 메서드
    public void incrementHelpfulCount() {
        this.helpfulCount++;
    }

    public void validateRating() {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("평점은 1-5점 사이여야 합니다.");
        }
    }
}
