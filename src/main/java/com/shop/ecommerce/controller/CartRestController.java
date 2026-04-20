package com.shop.ecommerce.controller;

import com.shop.ecommerce.dto.ApiResponse;
import com.shop.ecommerce.dto.CartDto;
import com.shop.ecommerce.dto.AddToCartRequest;
import com.shop.ecommerce.dto.UpdateCartItemRequest;
import com.shop.ecommerce.service.CartService;
import com.shop.ecommerce.util.AuthUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "장바구니", description = "장바구니 관리 API")
public class CartRestController {

    private final CartService cartService;
    private final AuthUtil authUtil;

    @GetMapping
    @Operation(summary = "장바구니 조회", description = "현재 사용자의 장바구니를 조회합니다.")
    public ResponseEntity<ApiResponse<CartDto>> getCart(Authentication authentication) {
        Long userId = authUtil.getCurrentUserId(authentication);
        CartDto cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(cart));
    }

    @PostMapping("/add")
    @Operation(summary = "장바구니에 상품 추가", description = "장바구니에 상품을 추가합니다.")
    public ResponseEntity<ApiResponse<CartDto>> addToCart(
            @RequestBody AddToCartRequest request,
            Authentication authentication) {
        
        Long userId = authUtil.getCurrentUserId(authentication);
        ApiResponse<CartDto> response = cartService.addToCart(
                userId, 
                request.getProductId(), 
                request.getQuantity()
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/items/{cartItemId}")
    @Operation(summary = "장바구니 아이템 수량 변경", description = "장바구니 아이템의 수량을 변경합니다.")
    public ResponseEntity<ApiResponse<CartDto>> updateCartItem(
            @PathVariable Long cartItemId,
            @RequestBody UpdateCartItemRequest request,
            Authentication authentication) {
        
        Long userId = authUtil.getCurrentUserId(authentication);
        ApiResponse<CartDto> response = cartService.updateCartItem(
                userId, 
                cartItemId, 
                request.getQuantity()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/items/{cartItemId}")
    @Operation(summary = "장바구니에서 상품 제거", description = "장바구니에서 특정 상품을 제거합니다.")
    public ResponseEntity<ApiResponse<CartDto>> removeFromCart(
            @PathVariable Long cartItemId,
            Authentication authentication) {
        
        Long userId = authUtil.getCurrentUserId(authentication);
        ApiResponse<CartDto> response = cartService.removeFromCart(userId, cartItemId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/clear")
    @Operation(summary = "장바구니 비우기", description = "장바구니의 모든 상품을 제거합니다.")
    public ResponseEntity<ApiResponse<Void>> clearCart(Authentication authentication) {
        Long userId = authUtil.getCurrentUserId(authentication);
        ApiResponse<Void> response = cartService.clearCart(userId);
        return ResponseEntity.ok(response);
    }
}