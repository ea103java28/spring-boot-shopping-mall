package com.tony.dto;

import lombok.Data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
public class BuyItem {

    @NotNull
    private Integer productId;

    @Min(1)
    @NotNull
    private Integer quantity;
}
