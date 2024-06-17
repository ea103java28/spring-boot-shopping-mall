package com.tony.model;

import lombok.Data;

@Data
public class OrderItem {

    // 對應到order_item table
    private Integer orderItemId;
    private Integer orderId;
    private Integer productId;
    private Integer quantity;
    private Integer amount;

    // 對應到product table
    private String productName;
    private String imageUrl;
}
