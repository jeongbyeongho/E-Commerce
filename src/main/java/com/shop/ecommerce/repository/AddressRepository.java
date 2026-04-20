package com.shop.ecommerce.repository;

import com.shop.ecommerce.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    
    // 사용자별 주소 조회
    List<Address> findByUserId(Long userId);
    
    // 사용자의 기본 배송지 조회
    Optional<Address> findByUserIdAndIsDefaultTrue(Long userId);
    
    // 사용자의 주소 개수
    long countByUserId(Long userId);
}
