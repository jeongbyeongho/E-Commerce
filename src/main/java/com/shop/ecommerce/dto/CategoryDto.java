package com.shop.ecommerce.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private Long id;
    private String name;
    private String description;
    private String code;
    private Boolean isActive;
    private Integer displayOrder;
    private Long parentId;
    private String parentName;
    private List<CategoryDto> children;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class CategoryCreateRequest {
    private String name;
    private String description;
    private String code;
    private Integer displayOrder;
    private Long parentId;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class CategoryUpdateRequest {
    private String name;
    private String description;
    private String code;
    private Boolean isActive;
    private Integer displayOrder;
    private Long parentId;
}
