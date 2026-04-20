package com.shop.ecommerce.controller;

import com.shop.ecommerce.dto.ApiResponse;
import com.shop.ecommerce.dto.ProductDto;
import com.shop.ecommerce.entity.Product;
import com.shop.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "상품", description = "상품 관리 API")
public class ProductRestController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "상품 목록 조회", description = "모든 상품을 페이징하여 조회합니다.")
    public ResponseEntity<ApiResponse<Page<ProductDto>>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDto> products = productService.findAll(pageable)
                .map(productService::toDto);
        
        return ResponseEntity.ok(ApiResponse.success(products));
    }

    @GetMapping("/search")
    @Operation(summary = "상품 검색", description = "키워드, 카테고리, 가격 범위 등으로 상품을 검색합니다.")
    public ResponseEntity<ApiResponse<Page<ProductDto>>> searchProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Page<ProductDto> products = productService.searchProducts(
                keyword, categoryId, minPrice, maxPrice, 
                sortBy, sortDirection, page, size
        ).map(productService::toDto);
        
        return ResponseEntity.ok(ApiResponse.success(products));
    }

    @GetMapping("/{id}")
    @Operation(summary = "상품 상세 조회", description = "특정 상품의 상세 정보를 조회합니다.")
    public ResponseEntity<ApiResponse<ProductDto>> getProduct(@PathVariable Long id) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
        
        // 조회수 증가
        productService.incrementViewCount(id);
        
        return ResponseEntity.ok(ApiResponse.success(productService.toDto(product)));
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "카테고리별 상품 조회", description = "특정 카테고리의 상품을 조회합니다.")
    public ResponseEntity<ApiResponse<List<ProductDto>>> getProductsByCategory(@PathVariable Long categoryId) {
        List<ProductDto> products = productService.findByCategoryId(categoryId).stream()
                .map(productService::toDto)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(ApiResponse.success(products));
    }

    @GetMapping("/top-selling")
    @Operation(summary = "인기 상품", description = "판매량이 높은 상품을 조회합니다.")
    public ResponseEntity<ApiResponse<Page<ProductDto>>> getTopSellingProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDto> products = productService.findAll(pageable)
                .map(productService::toDto);
        
        return ResponseEntity.ok(ApiResponse.success(products));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "상품 등록", description = "새로운 상품을 등록합니다. (관리자 전용)")
    public ResponseEntity<ApiResponse<ProductDto>> createProduct(@RequestBody ProductDto productDto) {
        Product product = productService.createProduct(productDto);
        return ResponseEntity.ok(ApiResponse.success("상품이 등록되었습니다.", productService.toDto(product)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "상품 수정", description = "기존 상품 정보를 수정합니다. (관리자 전용)")
    public ResponseEntity<ApiResponse<ProductDto>> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductDto productDto) {
        
        Product product = productService.updateProduct(id, productDto);
        return ResponseEntity.ok(ApiResponse.success("상품이 수정되었습니다.", productService.toDto(product)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "상품 삭제", description = "상품을 삭제합니다. (관리자 전용)")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success("상품이 삭제되었습니다.", null));
    }
}

