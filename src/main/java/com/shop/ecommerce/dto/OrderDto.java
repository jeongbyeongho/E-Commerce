package com.shop.ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {
    private Long id;
    private String orderNumber;
    private Long userId;
    private String username;
    private String status;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private BigDecimal shippingFee;
    private BigDecimal finalAmount;
    private String paymentMethod;
    private String paymentStatus;
    private String shippingAddress;
    private String recipientName;
    private String recipientPhone;
    private String zipCode;
    private String orderMemo;
    private String trackingNumber;
    private LocalDateTime paidAt;
    private LocalDateTime shippedAt;
    private LocalDateTime deliveredAt;
    private LocalDateTime createdAt;
    private List<OrderItemDto> orderItems;

    // 기본 생성자
    public OrderDto() {}

    // 전체 생성자
    public OrderDto(Long id, String orderNumber, Long userId, String username, String status,
                    BigDecimal totalAmount, BigDecimal discountAmount, BigDecimal shippingFee,
                    BigDecimal finalAmount, String paymentMethod, String paymentStatus,
                    String shippingAddress, String recipientName, String recipientPhone,
                    String zipCode, String orderMemo, String trackingNumber,
                    LocalDateTime paidAt, LocalDateTime shippedAt, LocalDateTime deliveredAt,
                    LocalDateTime createdAt, List<OrderItemDto> orderItems) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.userId = userId;
        this.username = username;
        this.status = status;
        this.totalAmount = totalAmount;
        this.discountAmount = discountAmount;
        this.shippingFee = shippingFee;
        this.finalAmount = finalAmount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.shippingAddress = shippingAddress;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
        this.zipCode = zipCode;
        this.orderMemo = orderMemo;
        this.trackingNumber = trackingNumber;
        this.paidAt = paidAt;
        this.shippedAt = shippedAt;
        this.deliveredAt = deliveredAt;
        this.createdAt = createdAt;
        this.orderItems = orderItems;
    }

    // Getter 메서드들
    public Long getId() { return id; }
    public String getOrderNumber() { return orderNumber; }
    public Long getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getStatus() { return status; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public BigDecimal getDiscountAmount() { return discountAmount; }
    public BigDecimal getShippingFee() { return shippingFee; }
    public BigDecimal getFinalAmount() { return finalAmount; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getPaymentStatus() { return paymentStatus; }
    public String getShippingAddress() { return shippingAddress; }
    public String getRecipientName() { return recipientName; }
    public String getRecipientPhone() { return recipientPhone; }
    public String getZipCode() { return zipCode; }
    public String getOrderMemo() { return orderMemo; }
    public String getTrackingNumber() { return trackingNumber; }
    public LocalDateTime getPaidAt() { return paidAt; }
    public LocalDateTime getShippedAt() { return shippedAt; }
    public LocalDateTime getDeliveredAt() { return deliveredAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<OrderItemDto> getOrderItems() { return orderItems; }

    // Setter 메서드들
    public void setId(Long id) { this.id = id; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setUsername(String username) { this.username = username; }
    public void setStatus(String status) { this.status = status; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public void setDiscountAmount(BigDecimal discountAmount) { this.discountAmount = discountAmount; }
    public void setShippingFee(BigDecimal shippingFee) { this.shippingFee = shippingFee; }
    public void setFinalAmount(BigDecimal finalAmount) { this.finalAmount = finalAmount; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }
    public void setRecipientPhone(String recipientPhone) { this.recipientPhone = recipientPhone; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
    public void setOrderMemo(String orderMemo) { this.orderMemo = orderMemo; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }
    public void setPaidAt(LocalDateTime paidAt) { this.paidAt = paidAt; }
    public void setShippedAt(LocalDateTime shippedAt) { this.shippedAt = shippedAt; }
    public void setDeliveredAt(LocalDateTime deliveredAt) { this.deliveredAt = deliveredAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setOrderItems(List<OrderItemDto> orderItems) { this.orderItems = orderItems; }

    // Builder 패턴
    public static OrderDtoBuilder builder() {
        return new OrderDtoBuilder();
    }

    public static class OrderDtoBuilder {
        private Long id;
        private String orderNumber;
        private Long userId;
        private String username;
        private String status;
        private BigDecimal totalAmount;
        private BigDecimal discountAmount;
        private BigDecimal shippingFee;
        private BigDecimal finalAmount;
        private String paymentMethod;
        private String paymentStatus;
        private String shippingAddress;
        private String recipientName;
        private String recipientPhone;
        private String zipCode;
        private String orderMemo;
        private String trackingNumber;
        private LocalDateTime paidAt;
        private LocalDateTime shippedAt;
        private LocalDateTime deliveredAt;
        private LocalDateTime createdAt;
        private List<OrderItemDto> orderItems;

        public OrderDtoBuilder id(Long id) { this.id = id; return this; }
        public OrderDtoBuilder orderNumber(String orderNumber) { this.orderNumber = orderNumber; return this; }
        public OrderDtoBuilder userId(Long userId) { this.userId = userId; return this; }
        public OrderDtoBuilder username(String username) { this.username = username; return this; }
        public OrderDtoBuilder status(String status) { this.status = status; return this; }
        public OrderDtoBuilder totalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; return this; }
        public OrderDtoBuilder discountAmount(BigDecimal discountAmount) { this.discountAmount = discountAmount; return this; }
        public OrderDtoBuilder shippingFee(BigDecimal shippingFee) { this.shippingFee = shippingFee; return this; }
        public OrderDtoBuilder finalAmount(BigDecimal finalAmount) { this.finalAmount = finalAmount; return this; }
        public OrderDtoBuilder paymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; return this; }
        public OrderDtoBuilder paymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; return this; }
        public OrderDtoBuilder shippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; return this; }
        public OrderDtoBuilder recipientName(String recipientName) { this.recipientName = recipientName; return this; }
        public OrderDtoBuilder recipientPhone(String recipientPhone) { this.recipientPhone = recipientPhone; return this; }
        public OrderDtoBuilder zipCode(String zipCode) { this.zipCode = zipCode; return this; }
        public OrderDtoBuilder orderMemo(String orderMemo) { this.orderMemo = orderMemo; return this; }
        public OrderDtoBuilder trackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; return this; }
        public OrderDtoBuilder paidAt(LocalDateTime paidAt) { this.paidAt = paidAt; return this; }
        public OrderDtoBuilder shippedAt(LocalDateTime shippedAt) { this.shippedAt = shippedAt; return this; }
        public OrderDtoBuilder deliveredAt(LocalDateTime deliveredAt) { this.deliveredAt = deliveredAt; return this; }
        public OrderDtoBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public OrderDtoBuilder orderItems(List<OrderItemDto> orderItems) { this.orderItems = orderItems; return this; }

        public OrderDto build() {
            return new OrderDto(id, orderNumber, userId, username, status, totalAmount, discountAmount,
                              shippingFee, finalAmount, paymentMethod, paymentStatus, shippingAddress,
                              recipientName, recipientPhone, zipCode, orderMemo, trackingNumber,
                              paidAt, shippedAt, deliveredAt, createdAt, orderItems);
        }
    }
}
