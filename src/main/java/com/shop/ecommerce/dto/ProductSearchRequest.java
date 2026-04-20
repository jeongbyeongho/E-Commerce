package com.shop.ecommerce.dto;

import java.math.BigDecimal;

public class ProductSearchRequest {
    private String keyword;
    private Long categoryId;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String sortBy; // price, name, rating, sales, createdAt
    private String sortDirection; // asc, desc
    private Integer page;
    private Integer size;

    // 기본 생성자
    public ProductSearchRequest() {}

    // 전체 생성자
    public ProductSearchRequest(String keyword, Long categoryId, BigDecimal minPrice, BigDecimal maxPrice,
                                String sortBy, String sortDirection, Integer page, Integer size) {
        this.keyword = keyword;
        this.categoryId = categoryId;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
        this.page = page;
        this.size = size;
    }

    // Getter 메서드들
    public String getKeyword() { return keyword; }
    public Long getCategoryId() { return categoryId; }
    public BigDecimal getMinPrice() { return minPrice; }
    public BigDecimal getMaxPrice() { return maxPrice; }
    public String getSortBy() { return sortBy; }
    public String getSortDirection() { return sortDirection; }
    public Integer getPage() { return page; }
    public Integer getSize() { return size; }

    // Setter 메서드들
    public void setKeyword(String keyword) { this.keyword = keyword; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public void setMinPrice(BigDecimal minPrice) { this.minPrice = minPrice; }
    public void setMaxPrice(BigDecimal maxPrice) { this.maxPrice = maxPrice; }
    public void setSortBy(String sortBy) { this.sortBy = sortBy; }
    public void setSortDirection(String sortDirection) { this.sortDirection = sortDirection; }
    public void setPage(Integer page) { this.page = page; }
    public void setSize(Integer size) { this.size = size; }
}