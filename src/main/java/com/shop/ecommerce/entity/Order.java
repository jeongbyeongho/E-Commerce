package com.shop.ecommerce.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus status = OrderStatus.PENDING;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(precision = 10, scale = 2)
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2)
    private BigDecimal shippingFee = BigDecimal.ZERO;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal finalAmount;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Column(length = 500)
    private String shippingAddress;

    @Column(length = 20)
    private String recipientName;

    @Column(length = 20)
    private String recipientPhone;

    @Column(length = 10)
    private String zipCode;

    @Column(columnDefinition = "TEXT")
    private String orderMemo;

    @Column(length = 100)
    private String trackingNumber;

    private LocalDateTime paidAt;

    private LocalDateTime shippedAt;

    private LocalDateTime deliveredAt;

    private LocalDateTime canceledAt;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // 연관관계
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    // 기본 생성자
    public Order() {}

    // 전체 생성자
    public Order(Long id, String orderNumber, User user, OrderStatus status, BigDecimal totalAmount,
                 BigDecimal discountAmount, BigDecimal shippingFee, BigDecimal finalAmount,
                 PaymentMethod paymentMethod, PaymentStatus paymentStatus, String shippingAddress,
                 String recipientName, String recipientPhone, String zipCode, String orderMemo,
                 String trackingNumber, LocalDateTime paidAt, LocalDateTime shippedAt,
                 LocalDateTime deliveredAt, LocalDateTime canceledAt, LocalDateTime createdAt,
                 LocalDateTime updatedAt, List<OrderItem> orderItems) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.user = user;
        this.status = status != null ? status : OrderStatus.PENDING;
        this.totalAmount = totalAmount;
        this.discountAmount = discountAmount != null ? discountAmount : BigDecimal.ZERO;
        this.shippingFee = shippingFee != null ? shippingFee : BigDecimal.ZERO;
        this.finalAmount = finalAmount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus != null ? paymentStatus : PaymentStatus.PENDING;
        this.shippingAddress = shippingAddress;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
        this.zipCode = zipCode;
        this.orderMemo = orderMemo;
        this.trackingNumber = trackingNumber;
        this.paidAt = paidAt;
        this.shippedAt = shippedAt;
        this.deliveredAt = deliveredAt;
        this.canceledAt = canceledAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.orderItems = orderItems != null ? orderItems : new ArrayList<>();
    }

    // Getter 메서드들
    public Long getId() { return id; }
    public String getOrderNumber() { return orderNumber; }
    public User getUser() { return user; }
    public OrderStatus getStatus() { return status; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public BigDecimal getDiscountAmount() { return discountAmount; }
    public BigDecimal getShippingFee() { return shippingFee; }
    public BigDecimal getFinalAmount() { return finalAmount; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public String getShippingAddress() { return shippingAddress; }
    public String getRecipientName() { return recipientName; }
    public String getRecipientPhone() { return recipientPhone; }
    public String getZipCode() { return zipCode; }
    public String getOrderMemo() { return orderMemo; }
    public String getTrackingNumber() { return trackingNumber; }
    public LocalDateTime getPaidAt() { return paidAt; }
    public LocalDateTime getShippedAt() { return shippedAt; }
    public LocalDateTime getDeliveredAt() { return deliveredAt; }
    public LocalDateTime getCanceledAt() { return canceledAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public List<OrderItem> getOrderItems() { return orderItems; }

    // Setter 메서드들
    public void setId(Long id) { this.id = id; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }
    public void setUser(User user) { this.user = user; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public void setDiscountAmount(BigDecimal discountAmount) { this.discountAmount = discountAmount; }
    public void setShippingFee(BigDecimal shippingFee) { this.shippingFee = shippingFee; }
    public void setFinalAmount(BigDecimal finalAmount) { this.finalAmount = finalAmount; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
    public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }
    public void setRecipientPhone(String recipientPhone) { this.recipientPhone = recipientPhone; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
    public void setOrderMemo(String orderMemo) { this.orderMemo = orderMemo; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }
    public void setPaidAt(LocalDateTime paidAt) { this.paidAt = paidAt; }
    public void setShippedAt(LocalDateTime shippedAt) { this.shippedAt = shippedAt; }
    public void setDeliveredAt(LocalDateTime deliveredAt) { this.deliveredAt = deliveredAt; }
    public void setCanceledAt(LocalDateTime canceledAt) { this.canceledAt = canceledAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; }

    // Builder 패턴
    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    public static class OrderBuilder {
        private Long id;
        private String orderNumber;
        private User user;
        private OrderStatus status = OrderStatus.PENDING;
        private BigDecimal totalAmount;
        private BigDecimal discountAmount = BigDecimal.ZERO;
        private BigDecimal shippingFee = BigDecimal.ZERO;
        private BigDecimal finalAmount;
        private PaymentMethod paymentMethod;
        private PaymentStatus paymentStatus = PaymentStatus.PENDING;
        private String shippingAddress;
        private String recipientName;
        private String recipientPhone;
        private String zipCode;
        private String orderMemo;
        private String trackingNumber;
        private LocalDateTime paidAt;
        private LocalDateTime shippedAt;
        private LocalDateTime deliveredAt;
        private LocalDateTime canceledAt;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private List<OrderItem> orderItems = new ArrayList<>();

        public OrderBuilder id(Long id) { this.id = id; return this; }
        public OrderBuilder orderNumber(String orderNumber) { this.orderNumber = orderNumber; return this; }
        public OrderBuilder user(User user) { this.user = user; return this; }
        public OrderBuilder status(OrderStatus status) { this.status = status; return this; }
        public OrderBuilder totalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; return this; }
        public OrderBuilder discountAmount(BigDecimal discountAmount) { this.discountAmount = discountAmount; return this; }
        public OrderBuilder shippingFee(BigDecimal shippingFee) { this.shippingFee = shippingFee; return this; }
        public OrderBuilder finalAmount(BigDecimal finalAmount) { this.finalAmount = finalAmount; return this; }
        public OrderBuilder paymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; return this; }
        public OrderBuilder paymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; return this; }
        public OrderBuilder shippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; return this; }
        public OrderBuilder recipientName(String recipientName) { this.recipientName = recipientName; return this; }
        public OrderBuilder recipientPhone(String recipientPhone) { this.recipientPhone = recipientPhone; return this; }
        public OrderBuilder zipCode(String zipCode) { this.zipCode = zipCode; return this; }
        public OrderBuilder orderMemo(String orderMemo) { this.orderMemo = orderMemo; return this; }
        public OrderBuilder trackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; return this; }
        public OrderBuilder paidAt(LocalDateTime paidAt) { this.paidAt = paidAt; return this; }
        public OrderBuilder shippedAt(LocalDateTime shippedAt) { this.shippedAt = shippedAt; return this; }
        public OrderBuilder deliveredAt(LocalDateTime deliveredAt) { this.deliveredAt = deliveredAt; return this; }
        public OrderBuilder canceledAt(LocalDateTime canceledAt) { this.canceledAt = canceledAt; return this; }
        public OrderBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public OrderBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
        public OrderBuilder orderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; return this; }

        public Order build() {
            return new Order(id, orderNumber, user, status, totalAmount, discountAmount, shippingFee,
                           finalAmount, paymentMethod, paymentStatus, shippingAddress, recipientName,
                           recipientPhone, zipCode, orderMemo, trackingNumber, paidAt, shippedAt,
                           deliveredAt, canceledAt, createdAt, updatedAt, orderItems);
        }
    }

    // 비즈니스 메서드
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void calculateFinalAmount() {
        this.finalAmount = totalAmount
                .subtract(discountAmount)
                .add(shippingFee);
    }

    public void updateStatus(OrderStatus newStatus) {
        this.status = newStatus;
        
        switch (newStatus) {
            case PAID -> this.paidAt = LocalDateTime.now();
            case SHIPPED -> this.shippedAt = LocalDateTime.now();
            case DELIVERED -> this.deliveredAt = LocalDateTime.now();
            case CANCELED -> this.canceledAt = LocalDateTime.now();
        }
    }

    public boolean canCancel() {
        return status == OrderStatus.PENDING || status == OrderStatus.PAID;
    }
}
