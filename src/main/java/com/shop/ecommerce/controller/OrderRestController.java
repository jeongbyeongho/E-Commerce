package com.shop.ecommerce.controller;

import com.shop.ecommerce.dto.ApiResponse;
import com.shop.ecommerce.dto.OrderDto;
import com.shop.ecommerce.dto.CreateOrderFromCartRequest;
import com.shop.ecommerce.dto.CreateDirectOrderRequest;
import com.shop.ecommerce.dto.UpdateOrderStatusRequest;
import com.shop.ecommerce.service.OrderService;
import com.shop.ecommerce.util.AuthUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "주문", description = "주문 관리 API")
public class OrderRestController {

    private final OrderService orderService;
    private final AuthUtil authUtil;

    @GetMapping
    @Operation(summary = "주문 목록 조회", description = "현재 사용자의 주문 목록을 조회합니다.")
    public ResponseEntity<ApiResponse<Page<OrderDto>>> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication) {
        
        Long userId = authUtil.getCurrentUserId(authentication);
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<OrderDto> orders = orderService.getOrdersByUserId(userId, pageable);
        
        return ResponseEntity.ok(ApiResponse.success(orders));
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "주문 상세 조회", description = "특정 주문의 상세 정보를 조회합니다.")
    public ResponseEntity<ApiResponse<OrderDto>> getOrder(
            @PathVariable Long orderId,
            Authentication authentication) {
        
        Long userId = authUtil.getCurrentUserId(authentication);
        OrderDto order = orderService.getOrderById(orderId, userId);
        
        return ResponseEntity.ok(ApiResponse.success(order));
    }

    @PostMapping("/from-cart")
    @Operation(summary = "장바구니에서 주문 생성", description = "장바구니의 모든 상품으로 주문을 생성합니다.")
    public ResponseEntity<ApiResponse<OrderDto>> createOrderFromCart(
            @RequestBody CreateOrderFromCartRequest request,
            Authentication authentication) {
        
        Long userId = authUtil.getCurrentUserId(authentication);
        ApiResponse<OrderDto> response = orderService.createOrderFromCart(
                userId,
                request.getAddressId(),
                request.getPaymentMethod(),
                request.getOrderMemo()
        );
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/direct")
    @Operation(summary = "직접 주문 생성", description = "선택한 상품들로 바로 주문을 생성합니다.")
    public ResponseEntity<ApiResponse<OrderDto>> createDirectOrder(
            @RequestBody CreateDirectOrderRequest request,
            Authentication authentication) {
        
        Long userId = authUtil.getCurrentUserId(authentication);
        ApiResponse<OrderDto> response = orderService.createDirectOrder(
                userId,
                request.getItems(),
                request.getAddressId(),
                request.getPaymentMethod(),
                request.getOrderMemo()
        );
        
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{orderId}/cancel")
    @Operation(summary = "주문 취소", description = "주문을 취소합니다.")
    public ResponseEntity<ApiResponse<Void>> cancelOrder(
            @PathVariable Long orderId,
            Authentication authentication) {
        
        Long userId = authUtil.getCurrentUserId(authentication);
        ApiResponse<Void> response = orderService.cancelOrder(orderId, userId);
        
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{orderId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "주문 상태 변경", description = "주문 상태를 변경합니다. (관리자 전용)")
    public ResponseEntity<ApiResponse<OrderDto>> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody UpdateOrderStatusRequest request) {
        
        ApiResponse<OrderDto> response = orderService.updateOrderStatus(
                orderId,
                request.getStatus(),
                request.getTrackingNumber()
        );
        
        return ResponseEntity.ok(response);
    }
}