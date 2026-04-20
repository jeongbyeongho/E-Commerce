package com.shop.ecommerce.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {
    private Long id;
    private Long productId;
    private String productName;
    private Long userId;
    private String username;
    private Integer rating;
    private String title;
    private String content;
    private String imageUrl;
    private Integer helpfulCount;
    private Boolean isVerifiedPurchase;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class ReviewCreateRequest {
    private Long productId;
    private Integer rating;
    private String title;
    private String content;
    private String imageUrl;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class ReviewUpdateRequest {
    private Integer rating;
    private String title;
    private String content;
    private String imageUrl;
}
