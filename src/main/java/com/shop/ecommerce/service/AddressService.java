package com.shop.ecommerce.service;

import com.shop.ecommerce.dto.ApiResponse;
import com.shop.ecommerce.entity.Address;
import com.shop.ecommerce.entity.User;
import com.shop.ecommerce.repository.AddressRepository;
import com.shop.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public List<Address> getAddressesByUserId(Long userId) {
        return addressRepository.findByUserId(userId);
    }

    public Address getAddressById(Long addressId, Long userId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("배송지를 찾을 수 없습니다."));
        
        if (!address.getUser().getId().equals(userId)) {
            throw new RuntimeException("접근 권한이 없습니다.");
        }
        
        return address;
    }

    @Transactional
    public ApiResponse<Address> createAddress(Long userId, String recipientName, String phone, 
                                            String zipCode, String address, String detailAddress, 
                                            String addressType, Boolean isDefault) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

            // 기본 배송지로 설정하는 경우, 기존 기본 배송지 해제
            if (isDefault != null && isDefault) {
                addressRepository.findByUserIdAndIsDefaultTrue(userId)
                        .ifPresent(existingDefault -> {
                            existingDefault.setIsDefault(false);
                            addressRepository.save(existingDefault);
                        });
            }

            Address newAddress = Address.builder()
                    .user(user)
                    .recipientName(recipientName)
                    .phone(phone)
                    .zipCode(zipCode)
                    .address(address)
                    .detailAddress(detailAddress)
                    .addressType(addressType)
                    .isDefault(isDefault != null ? isDefault : false)
                    .build();

            addressRepository.save(newAddress);

            return ApiResponse.success("배송지가 등록되었습니다.", newAddress);

        } catch (Exception e) {
            return ApiResponse.error("배송지 등록 실패: " + e.getMessage());
        }
    }

    @Transactional
    public ApiResponse<Address> updateAddress(Long addressId, Long userId, String recipientName, String phone,
                                            String zipCode, String address, String detailAddress,
                                            String addressType, Boolean isDefault) {
        try {
            Address existingAddress = addressRepository.findById(addressId)
                    .orElseThrow(() -> new RuntimeException("배송지를 찾을 수 없습니다."));

            if (!existingAddress.getUser().getId().equals(userId)) {
                return ApiResponse.error("접근 권한이 없습니다.");
            }

            // 기본 배송지로 설정하는 경우, 기존 기본 배송지 해제
            if (isDefault != null && isDefault && !existingAddress.getIsDefault()) {
                addressRepository.findByUserIdAndIsDefaultTrue(userId)
                        .ifPresent(currentDefault -> {
                            currentDefault.setIsDefault(false);
                            addressRepository.save(currentDefault);
                        });
            }

            existingAddress.setRecipientName(recipientName);
            existingAddress.setPhone(phone);
            existingAddress.setZipCode(zipCode);
            existingAddress.setAddress(address);
            existingAddress.setDetailAddress(detailAddress);
            existingAddress.setAddressType(addressType);
            if (isDefault != null) {
                existingAddress.setIsDefault(isDefault);
            }

            addressRepository.save(existingAddress);

            return ApiResponse.success("배송지가 수정되었습니다.", existingAddress);

        } catch (Exception e) {
            return ApiResponse.error("배송지 수정 실패: " + e.getMessage());
        }
    }

    @Transactional
    public ApiResponse<Void> deleteAddress(Long addressId, Long userId) {
        try {
            Address address = addressRepository.findById(addressId)
                    .orElseThrow(() -> new RuntimeException("배송지를 찾을 수 없습니다."));

            if (!address.getUser().getId().equals(userId)) {
                return ApiResponse.error("접근 권한이 없습니다.");
            }

            addressRepository.delete(address);

            return ApiResponse.success("배송지가 삭제되었습니다.", null);

        } catch (Exception e) {
            return ApiResponse.error("배송지 삭제 실패: " + e.getMessage());
        }
    }

    @Transactional
    public ApiResponse<Address> setDefaultAddress(Long addressId, Long userId) {
        try {
            Address address = addressRepository.findById(addressId)
                    .orElseThrow(() -> new RuntimeException("배송지를 찾을 수 없습니다."));

            if (!address.getUser().getId().equals(userId)) {
                return ApiResponse.error("접근 권한이 없습니다.");
            }

            // 기존 기본 배송지 해제
            addressRepository.findByUserIdAndIsDefaultTrue(userId)
                    .ifPresent(existingDefault -> {
                        existingDefault.setIsDefault(false);
                        addressRepository.save(existingDefault);
                    });

            // 새로운 기본 배송지 설정
            address.setIsDefault(true);
            addressRepository.save(address);

            return ApiResponse.success("기본 배송지가 설정되었습니다.", address);

        } catch (Exception e) {
            return ApiResponse.error("기본 배송지 설정 실패: " + e.getMessage());
        }
    }
}