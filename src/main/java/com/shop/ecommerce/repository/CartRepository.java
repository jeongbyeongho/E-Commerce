package com.shop.ecommerce.repository;

import com.shop.ecommerce.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    
    // 사용자별 장바구니 조회
    Optional<Cart> findByUserId(Long userId);
    
    // 장바구니 아이템 포함 조회 (N+1 문제 해결)
    @Query("SELECT c FROM Cart c LEFT JOIN FETCH c.cartItems ci LEFT JOIN FETCH ci.product WHERE c.user.id = :userId")
    Optional<Cart> findByUserIdWithItems(@Param("userId") Long userId);
    
    // 사용자의 장바구니 존재 여부
    boolean existsByUserId(Long userId);
}
