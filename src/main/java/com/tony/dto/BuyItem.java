package com.tony.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class BuyItem {

    @NotNull
    private Integer productId;

    @Min(1)
    @NotNull
    private Integer quantity;
}
