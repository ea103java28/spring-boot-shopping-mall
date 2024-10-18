package com.tony.model;

import lombok.Data;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "order")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "total_amount")
    private Integer totalAmount;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

    // 回傳給前端的orderItemList

    /*
    在 JPA（Java Persistence API）中，@Transient 是一個標註（annotation）
    ，用來指示某個屬性（field）或方法不應該被持久化（persisted）。
    這意味著，當實體（entity）對象被保存到數據庫時，該屬性或方法不會被包括在內
     */
    @Transient
    private List<OrderItem> orderItemList;
}
