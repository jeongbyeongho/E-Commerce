package com.shop.ecommerce.repository;

import com.shop.ecommerce.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    // 주문번호로 조회
    Optional<Order> findByOrderNumber(String orderNumber);
    
    // 사용자별 주문 조회 (페이징)
    Page<Order> findByUserId(Long userId, Pageable pageable);
    
    // 사용자별 주문 조회 (리스트)
    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    // 주문 상태별 조회
    @Query("SELECT o FROM Order o WHERE o.status = :status ORDER BY o.createdAt DESC")
    List<Order> findByStatus(@Param("status") String status);
    
    // 특정 기간 주문 조회
    @Query("SELECT o FROM Order o WHERE o.createdAt BETWEEN :startDate AND :endDate")
    List<Order> findByDateRange(@Param("startDate") LocalDateTime startDate, 
                                @Param("endDate") LocalDateTime endDate);
    
    // 사용자의 최근 주문 조회
    @Query("SELECT o FROM Order o WHERE o.user.id = :userId ORDER BY o.createdAt DESC")
    List<Order> findRecentOrdersByUserId(@Param("userId") Long userId, Pageable pageable);
}
