package com.shop.ecommerce.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 100)
    private String name;

    @Column(length = 20)
    private String phone;

    @Column(length = 20)
    private String role = "ROLE_USER";

    private Boolean enabled = true;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // 연관관계
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    // 기본 생성자
    public User() {}

    // 전체 생성자
    public User(Long id, String username, String email, String password, String name, 
                String phone, String role, Boolean enabled, LocalDateTime createdAt, 
                LocalDateTime updatedAt, List<Order> orders, List<Review> reviews, 
                Cart cart, List<Address> addresses) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = role;
        this.enabled = enabled;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.orders = orders != null ? orders : new ArrayList<>();
        this.reviews = reviews != null ? reviews : new ArrayList<>();
        this.cart = cart;
        this.addresses = addresses != null ? addresses : new ArrayList<>();
    }

    // Getter 메서드들
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getRole() { return role; }
    public Boolean getEnabled() { return enabled; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public List<Order> getOrders() { return orders; }
    public List<Review> getReviews() { return reviews; }
    public Cart getCart() { return cart; }
    public List<Address> getAddresses() { return addresses; }

    // Setter 메서드들
    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setRole(String role) { this.role = role; }
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setOrders(List<Order> orders) { this.orders = orders; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }
    public void setCart(Cart cart) { this.cart = cart; }
    public void setAddresses(List<Address> addresses) { this.addresses = addresses; }

    // Builder 패턴
    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private Long id;
        private String username;
        private String email;
        private String password;
        private String name;
        private String phone;
        private String role = "ROLE_USER";
        private Boolean enabled = true;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private List<Order> orders = new ArrayList<>();
        private List<Review> reviews = new ArrayList<>();
        private Cart cart;
        private List<Address> addresses = new ArrayList<>();

        public UserBuilder id(Long id) { this.id = id; return this; }
        public UserBuilder username(String username) { this.username = username; return this; }
        public UserBuilder email(String email) { this.email = email; return this; }
        public UserBuilder password(String password) { this.password = password; return this; }
        public UserBuilder name(String name) { this.name = name; return this; }
        public UserBuilder phone(String phone) { this.phone = phone; return this; }
        public UserBuilder role(String role) { this.role = role; return this; }
        public UserBuilder enabled(Boolean enabled) { this.enabled = enabled; return this; }
        public UserBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public UserBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
        public UserBuilder orders(List<Order> orders) { this.orders = orders; return this; }
        public UserBuilder reviews(List<Review> reviews) { this.reviews = reviews; return this; }
        public UserBuilder cart(Cart cart) { this.cart = cart; return this; }
        public UserBuilder addresses(List<Address> addresses) { this.addresses = addresses; return this; }

        public User build() {
            return new User(id, username, email, password, name, phone, role, enabled, 
                          createdAt, updatedAt, orders, reviews, cart, addresses);
        }
    }
}