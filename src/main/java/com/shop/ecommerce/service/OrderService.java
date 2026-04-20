package com.shop.ecommerce.service;

import com.shop.ecommerce.dto.ApiResponse;
import com.shop.ecommerce.dto.OrderDto;
import com.shop.ecommerce.dto.OrderItemDto;
import com.shop.ecommerce.dto.OrderItemRequest;
import com.shop.ecommerce.entity.*;
import com.shop.ecommerce.model.Product;
import com.shop.ecommerce.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;
    private final CartRepository cartRepository;

    public Page<OrderDto> getOrdersByUserId(Long userId, Pageable pageable) {
        return orderRepository.findByUserId(userId, pageable)
                .map(this::toDto);
    }

    public OrderDto getOrderById(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다."));
        
        // 본인 주문인지 확인
        if (!order.getUser().getId().equals(userId)) {
            throw new RuntimeException("접근 권한이 없습니다.");
        }
        
        return toDto(order);
    }

    @Transactional
    public ApiResponse<OrderDto> createOrderFromCart(Long userId, Long addressId, String paymentMethod, String orderMemo) {
        try {
            // 사용자 조회
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

            // 배송지 조회
            Address address = addressRepository.findById(addressId)
                    .orElseThrow(() -> new RuntimeException("배송지를 찾을 수 없습니다."));

            // 장바구니 조회
            Cart cart = cartRepository.findByUserIdWithItems(userId)
                    .orElseThrow(() -> new RuntimeException("장바구니를 찾을 수 없습니다."));

            if (cart.getCartItems().isEmpty()) {
                return ApiResponse.error("장바구니가 비어있습니다.");
            }

            // 재고 확인
            for (CartItem cartItem : cart.getCartItems()) {
                Product product = cartItem.getProduct();
                if (product.getStock() < cartItem.getQuantity()) {
                    return ApiResponse.error(product.getName() + "의 재고가 부족합니다. 현재 재고: " + product.getStock());
                }
            }

            // 주문 생성
            Order order = Order.builder()
                    .orderNumber(generateOrderNumber())
                    .user(user)
                    .status(OrderStatus.PENDING)
                    .totalAmount(cart.getTotalPrice())
                    .discountAmount(BigDecimal.ZERO)
                    .shippingFee(calculateShippingFee(cart.getTotalPrice()))
                    .paymentMethod(PaymentMethod.valueOf(paymentMethod))
                    .shippingAddress(address.getFullAddress())
                    .recipientName(address.getRecipientName())
                    .recipientPhone(address.getPhone())
                    .zipCode(address.getZipCode())
                    .orderMemo(orderMemo)
                    .build();

            // 최종 금액 계산
            order.calculateFinalAmount();

            // 주문 아이템 생성
            for (CartItem cartItem : cart.getCartItems()) {
                OrderItem orderItem = OrderItem.createOrderItem(cartItem.getProduct(), cartItem.getQuantity());
                order.addOrderItem(orderItem);
            }

            // 주문 저장
            orderRepository.save(order);

            // 장바구니 비우기
            cart.clear();
            cartRepository.save(cart);

            return ApiResponse.success("주문이 완료되었습니다.", toDto(order));

        } catch (Exception e) {
            return ApiResponse.error("주문 생성 실패: " + e.getMessage());
        }
    }

    @Transactional
    public ApiResponse<OrderDto> createDirectOrder(Long userId, List<OrderItemRequest> items, Long addressId, 
                                                   String paymentMethod, String orderMemo) {
        try {
            // 사용자 조회
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

            // 배송지 조회
            Address address = addressRepository.findById(addressId)
                    .orElseThrow(() -> new RuntimeException("배송지를 찾을 수 없습니다."));

            // 상품 및 재고 확인
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (OrderItemRequest itemRequest : items) {
                Product product = productRepository.findById(itemRequest.getProductId())
                        .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
                
                if (product.getStock() < itemRequest.getQuantity()) {
                    return ApiResponse.error(product.getName() + "의 재고가 부족합니다. 현재 재고: " + product.getStock());
                }
                
                totalAmount = totalAmount.add(product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity())));
            }

            // 주문 생성
            Order order = Order.builder()
                    .orderNumber(generateOrderNumber())
                    .user(user)
                    .status(OrderStatus.PENDING)
                    .totalAmount(totalAmount)
                    .discountAmount(BigDecimal.ZERO)
                    .shippingFee(calculateShippingFee(totalAmount))
                    .paymentMethod(PaymentMethod.valueOf(paymentMethod))
                    .shippingAddress(address.getFullAddress())
                    .recipientName(address.getRecipientName())
                    .recipientPhone(address.getPhone())
                    .zipCode(address.getZipCode())
                    .orderMemo(orderMemo)
                    .build();

            // 최종 금액 계산
            order.calculateFinalAmount();

            // 주문 아이템 생성
            for (OrderItemRequest itemRequest : items) {
                Product product = productRepository.findById(itemRequest.getProductId())
                        .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
                
                OrderItem orderItem = OrderItem.createOrderItem(product, itemRequest.getQuantity());
                order.addOrderItem(orderItem);
            }

            // 주문 저장
            orderRepository.save(order);

            return ApiResponse.success("주문이 완료되었습니다.", toDto(order));

        } catch (Exception e) {
            return ApiResponse.error("주문 생성 실패: " + e.getMessage());
        }
    }

    @Transactional
    public ApiResponse<OrderDto> updateOrderStatus(Long orderId, String status, String trackingNumber) {
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다."));

            order.updateStatus(OrderStatus.valueOf(status));
            
            if (trackingNumber != null && !trackingNumber.isEmpty()) {
                order.setTrackingNumber(trackingNumber);
            }

            orderRepository.save(order);

            return ApiResponse.success("주문 상태가 업데이트되었습니다.", toDto(order));

        } catch (Exception e) {
            return ApiResponse.error("주문 상태 업데이트 실패: " + e.getMessage());
        }
    }

    @Transactional
    public ApiResponse<Void> cancelOrder(Long orderId, Long userId) {
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다."));

            // 본인 주문인지 확인
            if (!order.getUser().getId().equals(userId)) {
                return ApiResponse.error("접근 권한이 없습니다.");
            }

            // 취소 가능한 상태인지 확인
            if (!order.canCancel()) {
                return ApiResponse.error("취소할 수 없는 주문 상태입니다.");
            }

            // 재고 복구
            for (OrderItem orderItem : order.getOrderItems()) {
                Product product = orderItem.getProduct();
                product.increaseStock(orderItem.getQuantity());
                productRepository.save(product);
            }

            // 주문 상태 변경
            order.updateStatus(OrderStatus.CANCELED);
            orderRepository.save(order);

            return ApiResponse.success("주문이 취소되었습니다.", null);

        } catch (Exception e) {
            return ApiResponse.error("주문 취소 실패: " + e.getMessage());
        }
    }

    private String generateOrderNumber() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String random = String.valueOf((int)(Math.random() * 1000));
        return "ORD" + timestamp + random;
    }

    private BigDecimal calculateShippingFee(BigDecimal totalAmount) {
        // 5만원 이상 무료배송
        if (totalAmount.compareTo(BigDecimal.valueOf(50000)) >= 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(3000); // 기본 배송비 3000원
    }

    private OrderDto toDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .userId(order.getUser().getId())
                .username(order.getUser().getUsername())
                .status(order.getStatus().name())
                .totalAmount(order.getTotalAmount())
                .discountAmount(order.getDiscountAmount())
                .shippingFee(order.getShippingFee())
                .finalAmount(order.getFinalAmount())
                .paymentMethod(order.getPaymentMethod() != null ? order.getPaymentMethod().name() : null)
                .paymentStatus(order.getPaymentStatus().name())
                .shippingAddress(order.getShippingAddress())
                .recipientName(order.getRecipientName())
                .recipientPhone(order.getRecipientPhone())
                .zipCode(order.getZipCode())
                .orderMemo(order.getOrderMemo())
                .trackingNumber(order.getTrackingNumber())
                .paidAt(order.getPaidAt())
                .shippedAt(order.getShippedAt())
                .deliveredAt(order.getDeliveredAt())
                .createdAt(order.getCreatedAt())
                .orderItems(order.getOrderItems().stream()
                        .map(this::toOrderItemDto)
                        .collect(Collectors.toList()))
                .build();
    }

    private OrderItemDto toOrderItemDto(OrderItem orderItem) {
        return OrderItemDto.builder()
                .id(orderItem.getId())
                .productId(orderItem.getProduct().getId())
                .productName(orderItem.getProductName())
                .price(orderItem.getPrice())
                .quantity(orderItem.getQuantity())
                .totalPrice(orderItem.getTotalPrice())
                .build();
    }
}