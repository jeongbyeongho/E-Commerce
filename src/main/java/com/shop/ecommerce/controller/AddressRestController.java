package com.shop.ecommerce.controller;

import com.shop.ecommerce.dto.ApiResponse;
import com.shop.ecommerce.dto.CreateAddressRequest;
import com.shop.ecommerce.dto.UpdateAddressRequest;
import com.shop.ecommerce.entity.Address;
import com.shop.ecommerce.service.AddressService;
import com.shop.ecommerce.util.AuthUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
@Tag(name = "배송지", description = "배송지 관리 API")
public class AddressRestController {

    private final AddressService addressService;
    private final AuthUtil authUtil;

    @GetMapping
    @Operation(summary = "배송지 목록 조회", description = "현재 사용자의 배송지 목록을 조회합니다.")
    public ResponseEntity<ApiResponse<List<Address>>> getAddresses(Authentication authentication) {
        Long userId = authUtil.getCurrentUserId(authentication);
        List<Address> addresses = addressService.getAddressesByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(addresses));
    }

    @GetMapping("/{addressId}")
    @Operation(summary = "배송지 상세 조회", description = "특정 배송지의 상세 정보를 조회합니다.")
    public ResponseEntity<ApiResponse<Address>> getAddress(
            @PathVariable Long addressId,
            Authentication authentication) {
        
        Long userId = authUtil.getCurrentUserId(authentication);
        Address address = addressService.getAddressById(addressId, userId);
        return ResponseEntity.ok(ApiResponse.success(address));
    }

    @PostMapping
    @Operation(summary = "배송지 등록", description = "새로운 배송지를 등록합니다.")
    public ResponseEntity<ApiResponse<Address>> createAddress(
            @RequestBody CreateAddressRequest request,
            Authentication authentication) {
        
        Long userId = authUtil.getCurrentUserId(authentication);
        ApiResponse<Address> response = addressService.createAddress(
                userId,
                request.getRecipientName(),
                request.getPhone(),
                request.getZipCode(),
                request.getAddress(),
                request.getDetailAddress(),
                request.getAddressType(),
                request.getIsDefault()
        );
        
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{addressId}")
    @Operation(summary = "배송지 수정", description = "기존 배송지 정보를 수정합니다.")
    public ResponseEntity<ApiResponse<Address>> updateAddress(
            @PathVariable Long addressId,
            @RequestBody UpdateAddressRequest request,
            Authentication authentication) {
        
        Long userId = authUtil.getCurrentUserId(authentication);
        ApiResponse<Address> response = addressService.updateAddress(
                addressId,
                userId,
                request.getRecipientName(),
                request.getPhone(),
                request.getZipCode(),
                request.getAddress(),
                request.getDetailAddress(),
                request.getAddressType(),
                request.getIsDefault()
        );
        
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{addressId}")
    @Operation(summary = "배송지 삭제", description = "배송지를 삭제합니다.")
    public ResponseEntity<ApiResponse<Void>> deleteAddress(
            @PathVariable Long addressId,
            Authentication authentication) {
        
        Long userId = authUtil.getCurrentUserId(authentication);
        ApiResponse<Void> response = addressService.deleteAddress(addressId, userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{addressId}/default")
    @Operation(summary = "기본 배송지 설정", description = "특정 배송지를 기본 배송지로 설정합니다.")
    public ResponseEntity<ApiResponse<Address>> setDefaultAddress(
            @PathVariable Long addressId,
            Authentication authentication) {
        
        Long userId = authUtil.getCurrentUserId(authentication);
        ApiResponse<Address> response = addressService.setDefaultAddress(addressId, userId);
        return ResponseEntity.ok(response);
    }
}