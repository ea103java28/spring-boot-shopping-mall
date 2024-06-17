package com.tony.service;

import com.tony.dto.CreateOrderRequest;
import com.tony.dto.OrderQueryParams;
import com.tony.model.Order;

import java.util.List;

public interface OrderService {

    void isUserExist(Integer userId);

    Integer countOrder(OrderQueryParams orderQueryParams);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Order getOrderById(Integer orderId);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
