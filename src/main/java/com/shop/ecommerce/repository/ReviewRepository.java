package com.shop.ecommerce.repository;

import com.shop.ecommerce.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    // 상품별 리뷰 조회 (페이징)
    Page<Review> findByProductId(Long productId, Pageable pageable);
    
    // 사용자별 리뷰 조회
    List<Review> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    // 상품의 평균 평점 계산
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.product.id = :productId")
    Double calculateAverageRating(@Param("productId") Long productId);
    
    // 상품의 리뷰 개수
    long countByProductId(Long productId);
    
    // 평점별 리뷰 조회
    List<Review> findByProductIdAndRating(Long productId, Integer rating);
    
    // 인증된 구매자 리뷰만 조회
    @Query("SELECT r FROM Review r WHERE r.product.id = :productId AND r.isVerifiedPurchase = true")
    Page<Review> findVerifiedReviewsByProductId(@Param("productId") Long productId, Pageable pageable);
    
    // 도움이 많이 된 리뷰 조회
    @Query("SELECT r FROM Review r WHERE r.product.id = :productId ORDER BY r.helpfulCount DESC")
    List<Review> findMostHelpfulReviews(@Param("productId") Long productId, Pageable pageable);
}
