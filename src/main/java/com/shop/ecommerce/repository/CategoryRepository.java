package com.shop.ecommerce.repository;

import com.shop.ecommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    // 최상위 카테고리 조회 (부모가 없는 카테고리)
    List<Category> findByParentIsNull();
    
    // 특정 부모의 자식 카테고리 조회
    List<Category> findByParentId(Long parentId);
    
    // 활성화된 카테고리만 조회
    List<Category> findByIsActiveTrue();
    
    // 코드로 카테고리 조회
    Optional<Category> findByCode(String code);
    
    // 이름으로 검색
    List<Category> findByNameContaining(String name);
    
    // 정렬 순서대로 조회
    @Query("SELECT c FROM Category c WHERE c.isActive = true ORDER BY c.displayOrder ASC")
    List<Category> findAllActiveOrderByDisplayOrder();
}
