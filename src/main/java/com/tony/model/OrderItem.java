package com.tony.model;

import lombok.Data;

import jakarta.persistence.*;

@Entity
@Table(name = "order_item")
@Data
public class OrderItem {

    // 對應到order_item table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Integer orderItemId;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "amount")
    private Integer amount;

    // 對應到product table
    @Transient
    private String productName;
    @Transient
    private String imageUrl;
}
