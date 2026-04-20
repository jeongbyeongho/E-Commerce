package com.shop.ecommerce.repository;

import com.shop.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // 활성화된 상품만 조회
    Page<Product> findByIsActiveTrue(Pageable pageable);
    
    // 카테고리별 상품 조회
    List<Product> findByCategoryId(Long categoryId);
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);
    
    // 상품명으로 검색
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
    
    // 가격 범위로 검색
    Page<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    
    // 브랜드로 검색
    Page<Product> findByBrand(String brand, Pageable pageable);
    
    // 인기 상품 (판매량 기준)
    @Query("SELECT p FROM Product p WHERE p.isActive = true ORDER BY p.salesCount DESC")
    List<Product> findTopSellingProducts(Pageable pageable);
    
    // 신상품
    @Query("SELECT p FROM Product p WHERE p.isActive = true ORDER BY p.createdAt DESC")
    List<Product> findNewProducts(Pageable pageable);
    
    // 평점 높은 상품
    @Query("SELECT p FROM Product p WHERE p.isActive = true AND p.reviewCount > 0 ORDER BY p.averageRating DESC")
    List<Product> findTopRatedProducts(Pageable pageable);
    
    // 재고 부족 상품
    @Query("SELECT p FROM Product p WHERE p.stock < :threshold")
    List<Product> findLowStockProducts(@Param("threshold") int threshold);
}
