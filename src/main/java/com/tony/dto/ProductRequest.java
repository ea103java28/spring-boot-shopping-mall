package com.tony.dto;

import com.tony.constant.ProductCategory;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class ProductRequest {


    private Integer productId;

    @NotNull
    private String productName;

    @NotNull
    private ProductCategory category;

    @NotNull
    private String imageUrl;

    @NotNull
    private Integer price;

    @NotNull
    private Integer stock;

    private String description;
}
