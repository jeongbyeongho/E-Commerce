package com.shop.ecommerce.test;

import com.shop.ecommerce.dto.ApiResponse;
import com.shop.ecommerce.dto.CartDto;
import com.shop.ecommerce.entity.Cart;
import com.shop.ecommerce.entity.CartItem;
import com.shop.ecommerce.entity.User;
import com.shop.ecommerce.model.Product;
import com.shop.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * CartService 기능 테스트를 위한 간단한 테스트 클래스
 */
@Component
public class CartServiceTest implements CommandLineRunner {

    @Autowired
    private CartService cartService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== CartService 테스트 시작 ===");
        
        // 기본 객체 생성 테스트
        testBasicObjectCreation();
        
        System.out.println("=== CartService 테스트 완료 ===");
    }

    private void testBasicObjectCreation() {
        try {
            // User 객체 생성 테스트
            User user = User.builder()
                    .id(1L)
                    .username("testuser")
                    .email("test@example.com")
                    .password("password")
                    .name("테스트 사용자")
                    .build();
            
            System.out.println("User 생성 성공: " + user.getUsername());

            // Product 객체 생성 테스트
            Product product = Product.builder()
                    .id(1L)
                    .name("테스트 상품")
                    .price(new BigDecimal("10000"))
                    .stock(100)
                    .build();
            
            System.out.println("Product 생성 성공: " + product.getName());

            // Cart 객체 생성 테스트
            Cart cart = Cart.builder()
                    .id(1L)
                    .user(user)
                    .build();
            
            System.out.println("Cart 생성 성공: " + cart.getId());

            // CartItem 객체 생성 테스트
            CartItem cartItem = CartItem.builder()
                    .id(1L)
                    .cart(cart)
                    .product(product)
                    .quantity(2)
                    .build();
            
            System.out.println("CartItem 생성 성공: " + cartItem.getQuantity());
            System.out.println("CartItem 총 가격: " + cartItem.getTotalPrice());

            // ApiResponse 객체 생성 테스트
            ApiResponse<String> response = ApiResponse.success("테스트 성공");
            System.out.println("ApiResponse 생성 성공: " + response.getMessage());

        } catch (Exception e) {
            System.err.println("객체 생성 테스트 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }
}