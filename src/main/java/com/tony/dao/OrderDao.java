//package com.tony.dao;
//
//import com.tony.dto.OrderQueryParams;
//import com.tony.model.Order;
//import com.tony.model.OrderItem;
//
//import java.util.List;
//
//public interface OrderDao {
//
//    Integer countOrder(OrderQueryParams orderQueryParams);
//
//    List<Order> getOrders(OrderQueryParams orderQueryParams);
//
//    Order getOrderById(Integer orderId);
//
//    List<OrderItem> getOrderItemsByOrderId(Integer orderId);
//
//    Integer createOrder(Integer userId, Integer totalAmount);
//
//    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
//}
