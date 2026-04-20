package com.shop.ecommerce.controller;

import com.shop.ecommerce.dto.ApiResponse;
import com.shop.ecommerce.dto.UserRegisterRequest;
import com.shop.ecommerce.dto.UserLoginRequest;
import com.shop.ecommerce.dto.RefreshTokenRequest;
import com.shop.ecommerce.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "인증", description = "회원가입, 로그인, 토큰 관리 API")
public class AuthRestController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "회원가입", description = "새로운 사용자를 등록합니다.")
    public ResponseEntity<ApiResponse<Map<String, String>>> register(@RequestBody UserRegisterRequest request) {
        ApiResponse<Map<String, String>> response = authService.register(
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getName(),
                request.getPhone()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "사용자 인증 후 JWT 토큰을 발급합니다.")
    public ResponseEntity<ApiResponse<Map<String, String>>> login(@RequestBody UserLoginRequest request) {
        ApiResponse<Map<String, String>> response = authService.login(
                request.getUsername(),
                request.getPassword()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    @Operation(summary = "토큰 갱신", description = "리프레시 토큰으로 새로운 액세스 토큰을 발급합니다.")
    public ResponseEntity<ApiResponse<Map<String, String>>> refreshToken(@RequestBody RefreshTokenRequest request) {
        ApiResponse<Map<String, String>> response = authService.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/test")
    @Operation(summary = "인증 테스트", description = "인증된 사용자만 접근 가능한 테스트 엔드포인트")
    public ResponseEntity<ApiResponse<String>> test() {
        return ResponseEntity.ok(ApiResponse.success("인증 성공!"));
    }
}
