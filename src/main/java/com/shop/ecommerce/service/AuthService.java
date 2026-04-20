package com.shop.ecommerce.service;

import com.shop.ecommerce.dto.ApiResponse;
import com.shop.ecommerce.entity.User;
import com.shop.ecommerce.repository.UserRepository;
import com.shop.ecommerce.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Transactional
    public ApiResponse<Map<String, String>> register(String username, String email, String password, String name, String phone) {
        // 중복 체크
        if (userRepository.existsByUsername(username)) {
            return ApiResponse.error("이미 존재하는 사용자명입니다.");
        }
        if (userRepository.existsByEmail(email)) {
            return ApiResponse.error("이미 존재하는 이메일입니다.");
        }

        // 사용자 생성
        User user = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .phone(phone)
                .role("ROLE_USER")
                .enabled(true)
                .build();

        userRepository.save(user);

        // 토큰 생성
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String accessToken = jwtUtil.generateToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        tokens.put("username", username);

        return ApiResponse.success("회원가입이 완료되었습니다.", tokens);
    }

    public ApiResponse<Map<String, String>> login(String username, String password) {
        try {
            // 인증 시도
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            // 토큰 생성
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            String accessToken = jwtUtil.generateToken(userDetails);
            String refreshToken = jwtUtil.generateRefreshToken(userDetails);

            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("refreshToken", refreshToken);
            tokens.put("username", username);

            return ApiResponse.success("로그인 성공", tokens);
        } catch (Exception e) {
            return ApiResponse.error("아이디 또는 비밀번호가 올바르지 않습니다.");
        }
    }

    public ApiResponse<Map<String, String>> refreshToken(String refreshToken) {
        try {
            if (jwtUtil.validateToken(refreshToken)) {
                String username = jwtUtil.extractUsername(refreshToken);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                
                String newAccessToken = jwtUtil.generateToken(userDetails);
                
                Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", newAccessToken);
                tokens.put("refreshToken", refreshToken);
                
                return ApiResponse.success("토큰 갱신 성공", tokens);
            } else {
                return ApiResponse.error("유효하지 않은 리프레시 토큰입니다.");
            }
        } catch (Exception e) {
            return ApiResponse.error("토큰 갱신 실패: " + e.getMessage());
        }
    }
}
