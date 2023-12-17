package com.tony.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Order {
    // 對應到order table
    private Integer orderId;
    private Integer userId;
    private Integer totalAmount;
    private Date createdDate;
    private Date lastModifiedDate;

    // 回傳給前端的orderItemList
    private List<OrderItem> orderItemList;
}
