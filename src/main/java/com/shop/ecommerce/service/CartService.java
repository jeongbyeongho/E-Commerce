package com.shop.ecommerce.service;

import com.shop.ecommerce.dto.ApiResponse;
import com.shop.ecommerce.dto.CartDto;
import com.shop.ecommerce.dto.CartItemDto;
import com.shop.ecommerce.entity.Cart;
import com.shop.ecommerce.entity.CartItem;
import com.shop.ecommerce.entity.User;
import com.shop.ecommerce.model.Product;
import com.shop.ecommerce.repository.CartRepository;
import com.shop.ecommerce.repository.ProductRepository;
import com.shop.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartDto getCartByUserId(Long userId) {
        Cart cart = getOrCreateCart(userId);
        return toDto(cart);
    }

    @Transactional
    public ApiResponse<CartDto> addToCart(Long userId, Long productId, Integer quantity) {
        try {
            // 사용자와 상품 조회
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
            
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

            // 재고 확인
            if (product.getStock() < quantity) {
                return ApiResponse.error("재고가 부족합니다. 현재 재고: " + product.getStock());
            }

            // 장바구니 조회 또는 생성
            Cart cart = getOrCreateCart(userId);

            // 이미 장바구니에 있는 상품인지 확인
            Optional<CartItem> existingItem = cart.getCartItems().stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst();

            if (existingItem.isPresent()) {
                // 기존 아이템 수량 업데이트
                CartItem cartItem = existingItem.get();
                int newQuantity = cartItem.getQuantity() + quantity;
                
                if (product.getStock() < newQuantity) {
                    return ApiResponse.error("재고가 부족합니다. 현재 재고: " + product.getStock());
                }
                
                cartItem.setQuantity(newQuantity);
            } else {
                // 새 아이템 추가
                CartItem cartItem = CartItem.builder()
                        .cart(cart)
                        .product(product)
                        .quantity(quantity)
                        .build();
                
                cart.addItem(cartItem);
            }

            cartRepository.save(cart);
            return ApiResponse.success("장바구니에 상품이 추가되었습니다.", toDto(cart));
            
        } catch (Exception e) {
            return ApiResponse.error("장바구니 추가 실패: " + e.getMessage());
        }
    }

    @Transactional
    public ApiResponse<CartDto> updateCartItem(Long userId, Long cartItemId, Integer quantity) {
        try {
            Cart cart = cartRepository.findByUserId(userId)
                    .orElseThrow(() -> new RuntimeException("장바구니를 찾을 수 없습니다."));

            CartItem cartItem = cart.getCartItems().stream()
                    .filter(item -> item.getId().equals(cartItemId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("장바구니 아이템을 찾을 수 없습니다."));

            // 재고 확인
            if (cartItem.getProduct().getStock() < quantity) {
                return ApiResponse.error("재고가 부족합니다. 현재 재고: " + cartItem.getProduct().getStock());
            }

            cartItem.setQuantity(quantity);
            cartRepository.save(cart);

            return ApiResponse.success("장바구니가 업데이트되었습니다.", toDto(cart));
            
        } catch (Exception e) {
            return ApiResponse.error("장바구니 업데이트 실패: " + e.getMessage());
        }
    }

    @Transactional
    public ApiResponse<CartDto> removeFromCart(Long userId, Long cartItemId) {
        try {
            Cart cart = cartRepository.findByUserId(userId)
                    .orElseThrow(() -> new RuntimeException("장바구니를 찾을 수 없습니다."));

            CartItem cartItem = cart.getCartItems().stream()
                    .filter(item -> item.getId().equals(cartItemId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("장바구니 아이템을 찾을 수 없습니다."));

            cart.removeItem(cartItem);
            cartRepository.save(cart);

            return ApiResponse.success("상품이 장바구니에서 제거되었습니다.", toDto(cart));
            
        } catch (Exception e) {
            return ApiResponse.error("장바구니 제거 실패: " + e.getMessage());
        }
    }

    @Transactional
    public ApiResponse<Void> clearCart(Long userId) {
        try {
            Cart cart = cartRepository.findByUserId(userId)
                    .orElseThrow(() -> new RuntimeException("장바구니를 찾을 수 없습니다."));

            cart.clear();
            cartRepository.save(cart);

            return ApiResponse.success("장바구니가 비워졌습니다.", null);
            
        } catch (Exception e) {
            return ApiResponse.error("장바구니 비우기 실패: " + e.getMessage());
        }
    }

    private Cart getOrCreateCart(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
                    
                    Cart cart = Cart.builder()
                            .user(user)
                            .build();
                    
                    return cartRepository.save(cart);
                });
    }

    private CartDto toDto(Cart cart) {
        return CartDto.builder()
                .id(cart.getId())
                .userId(cart.getUser().getId())
                .items(cart.getCartItems().stream()
                        .map(this::toCartItemDto)
                        .collect(Collectors.toList()))
                .totalPrice(cart.getTotalPrice())
                .totalQuantity(cart.getTotalQuantity())
                .build();
    }

    private CartItemDto toCartItemDto(CartItem cartItem) {
        return CartItemDto.builder()
                .id(cartItem.getId())
                .productId(cartItem.getProduct().getId())
                .productName(cartItem.getProduct().getName())
                .price(cartItem.getProduct().getPrice())
                .quantity(cartItem.getQuantity())
                .totalPrice(cartItem.getTotalPrice())
                .imageUrl(cartItem.getProduct().getImageUrl())
                .stock(cartItem.getProduct().getStock())
                .build();
    }
}