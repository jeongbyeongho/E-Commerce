package com.shop.ecommerce.entity;

import com.shop.ecommerce.entity.Product;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(length = 50)
    private String code;

    private Boolean isActive = true;

    private Integer displayOrder = 0;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // 계층 구조 (부모-자식 관계)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Category> children = new ArrayList<>();

    // 상품 관계
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    // 기본 생성자
    public Category() {}

    // 전체 생성자
    public Category(Long id, String name, String description, String code, Boolean isActive,
                    Integer displayOrder, LocalDateTime createdAt, LocalDateTime updatedAt,
                    Category parent, List<Category> children, List<Product> products) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.code = code;
        this.isActive = isActive != null ? isActive : true;
        this.displayOrder = displayOrder != null ? displayOrder : 0;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.parent = parent;
        this.children = children != null ? children : new ArrayList<>();
        this.products = products != null ? products : new ArrayList<>();
    }

    // Getter 메서드들
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getCode() { return code; }
    public Boolean getIsActive() { return isActive; }
    public Integer getDisplayOrder() { return displayOrder; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public Category getParent() { return parent; }
    public List<Category> getChildren() { return children; }
    public List<Product> getProducts() { return products; }

    // Setter 메서드들
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setCode(String code) { this.code = code; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setParent(Category parent) { this.parent = parent; }
    public void setChildren(List<Category> children) { this.children = children; }
    public void setProducts(List<Product> products) { this.products = products; }

    // Builder 패턴
    public static CategoryBuilder builder() {
        return new CategoryBuilder();
    }

    public static class CategoryBuilder {
        private Long id;
        private String name;
        private String description;
        private String code;
        private Boolean isActive = true;
        private Integer displayOrder = 0;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Category parent;
        private List<Category> children = new ArrayList<>();
        private List<Product> products = new ArrayList<>();

        public CategoryBuilder id(Long id) { this.id = id; return this; }
        public CategoryBuilder name(String name) { this.name = name; return this; }
        public CategoryBuilder description(String description) { this.description = description; return this; }
        public CategoryBuilder code(String code) { this.code = code; return this; }
        public CategoryBuilder isActive(Boolean isActive) { this.isActive = isActive; return this; }
        public CategoryBuilder displayOrder(Integer displayOrder) { this.displayOrder = displayOrder; return this; }
        public CategoryBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public CategoryBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
        public CategoryBuilder parent(Category parent) { this.parent = parent; return this; }
        public CategoryBuilder children(List<Category> children) { this.children = children; return this; }
        public CategoryBuilder products(List<Product> products) { this.products = products; return this; }

        public Category build() {
            return new Category(id, name, description, code, isActive, displayOrder, createdAt,
                              updatedAt, parent, children, products);
        }
    }

    // 비즈니스 메서드
    public void addChild(Category child) {
        children.add(child);
        child.setParent(this);
    }

    public void removeChild(Category child) {
        children.remove(child);
        child.setParent(null);
    }
}
